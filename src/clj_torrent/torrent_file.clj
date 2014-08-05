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
(def peer-prefix "GK-")

;; (def torrent-state (atom {:peer-id (generate-peer-id)
;;                           :downloaded-so-far 0}))
(defn generate-peer-id []
  (str peer-prefix (clojure.string/join (take (- peer-id-length (count peer-prefix)) (.toString (UUID/randomUUID))))))

(def peer-id (generate-peer-id))

(defn parse-torrent-files [filenames]
  (map #(metadata/init-metadata %) filenames))

(defn parse-peer-bytes [peers]
  "Takes in a byte array and parses it for the ints that will make up IP addresses.
  Java bytes are signed which means more maths for those < 0"
  (into [] (map
            (fn [b] (let [int-val (int b)]
                      (if (< int-val 0)
                        (+ 256 int-val)
                        int-val)))
            peers)))

(defn to-ip-and-port [input]
  (let [[a b] (partition-all 4 input)]
    {:ip (string/join "." a)
     :port (+ (* 256 (first b)) (last b))}))

(defn parse-peers [peers]
  (map to-ip-and-port (partition 6 (parse-peer-bytes peers))))

(defn parse-tracker-response [response]
  (log/info "Parsing tracker response")
  (let [decoded (bdecode response {:str-keys? false :raw-keys ["peers"]})
        peers (parse-peers (:peers decoded))]
    (println "Parsed peer results")
    (log/info decoded)
    (println peers)))

(defn init-tracker-url [metadata]
  "Constructs a URL to connect to a tracker with correct encoding of info_hash, peer_id & length"
  (str (:announce metadata) "?info_hash=" (percent-encode (hexify (:info-hash metadata)))
       "&peer_id=" peer-id "&left=" (:length metadata)))

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
