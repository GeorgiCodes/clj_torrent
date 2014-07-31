(ns clj-torrent.torrent-file
  (:import (java.util UUID)
           [java.net URLEncoder])
  (:require [clj-torrent.metadata :as metadata]
            [clj-torrent.utils :refer :all]
            [org.httpkit.client :as http]
            [clojure.tools.logging :as log]
            [bencode.core :refer [bdecode]]
            [bencode.bencode :refer [decode encode]]
            [clojure.string :as string]))

(def peer-id-length 20)
(def peer-prefix "GK")

(defn generate-peer-id []
  (take peer-id-length (.toString (UUID/randomUUID))))

(defn parse-torrent-files [filenames]
  (map #(metadata/init-metadata %) filenames))

(defn parse-peers [peers]
  "Takes in a byte array and parses it for ip addresses and ports"
  (into [] (map (fn [b] (int b)) peers)))

(defn apply-if
  "Applies f to val if (pred val) is truthy. Otherwise,
  returns val."
  [pred f val]
  (if (pred val) (f val) val))

(defn to-ip-and-port
  [s]
  (let [[ip [a b]] (partition-all 4 (map int s))]
    {:ip (string/join "." ip)
     :port (+ b (* 256 a))}))

(defn prep-peers
  [peers]
  (apply-if (complement vector?) #(mapv to-ip-and-port (partition 6 %)) peers))

(defn parse-tracker-response [response]
  (log/info "Parsing tracker response")
  (let [decoded (bdecode response {:str-keys? false :raw-keys ["peers"]})
        peers (parse-peers (:peers decoded))
        parsed-peers (prep-peers (:peers decoded))]
    (log/info decoded)
    (println peers)
    (println parsed-peers)
    (class parsed-peers )
    (:ip parse-peers)
    (println "who is here")
    ))

(defn init-tracker-url [metadata]
  "Constructs a URL to connect to a tracker with correct encoding of info_hash"
  (str (:announce metadata) "?info_hash=" (percent-encode (hexify (:info-hash metadata)))
       "&peer_id=" "GK121212121212121212" "&left=" (:length metadata)))

(defn connect-to-tracker [metadata]
  (log/info "Connecting to tracker" (init-tracker-url metadata))
  (let [{:keys [status headers body error] :as resp}
        @(http/get (init-tracker-url metadata) {:as :byte-array})]
    (if error
      (log/error "Failed to connect to tracker: " error)
      (do
        (log/info "HTTP GET success: ")
        (parse-tracker-response body)))))

(defn connect-to-trackers [metadata-list]
  (log/info "Connecting to" (count metadata-list) "trackers")
  (connect-to-tracker (first metadata-list)))

(defn start! [filenames]
  (let [metadata-list (parse-torrent-files filenames)]
    (connect-to-trackers metadata-list)))

;; d8:completei0e10:downloadedi0e10:incompletei1e8:intervali1892e12:min intervali946e5:peers6:J\324\267\272\000\000e #clojure
;; d8:completei1e10:downloadedi2e10:incompletei1e8:intervali1749e12:min intervali874e5:peers12:J\324\267\272\000\000`~h\333\3529e #ruby
