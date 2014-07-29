(ns clj-torrent.torrent-file
  (:import (java.util UUID)
           [java.net URLEncoder])
  (:require [clj-torrent.metadata :as metadata]
            [org.httpkit.client :as http]
            [clojure.tools.logging :as log]
            [bencode.core :refer [bdecode]]
            [bencode.bencode :refer [decode encode]]))

(def peer-id-length 20)
(def peer-prefix "GK")

;; (take 4 "Bl\xAD\x10\x00\x00`~h\xDB\xEA9")
;; (map (comp byte int) "66")


(defn generate-peer-id []
  (take peer-id-length (.toString (UUID/randomUUID))))

(defn parse-torrent-files [filenames]
  (map #(metadata/init-metadata %) filenames))

(defn parse-peers [peers]
  "Takes in a byte array and parses it for ip addresses and ports"
  )

;; (into [] (map (fn [b] (int b)) peers))

(defn parse-tracker-response [response]
  (log/info "Parsing tracker response" response)
  (let [decoded (decode response)
        peers (parse-peers (get decoded "peers"))]
    (println decoded)
    (println (get decoded "peers"))
    ))

(defn init-tracker-url [metadata]
  "Constructs a URL to connect to a tracker with correct encoding of info_hash"
  (str (:announce metadata) "?info_hash=" (URLEncoder/encode (String. (:info-hash metadata)) "ISO-8859-1")
       "&peer_id=" "GK121212121212121212" "&left=" (:length metadata)))

(defn connect-to-tracker [metadata]
  (log/info "Connecting to tracker" (init-tracker-url metadata))
  (let [{:keys [status headers body error] :as resp}
        @(http/get (init-tracker-url metadata))]
    (if error
      (log/error "Failed to connect to tracker: " error)
      (do
        (log/info "HTTP GET success: " body)
        (parse-tracker-response body)))))

(defn connect-to-trackers [metadata-list]
  (log/info "Connecting to" (count metadata-list) "trackers")
  (connect-to-tracker (first metadata-list)))

(defn start! [filenames]
  (let [metadata-list (parse-torrent-files filenames)]
    (connect-to-trackers metadata-list)))

;; d8:completei0e10:downloadedi0e10:incompletei1e8:intervali1892e12:min intervali946e5:peers6:J\324\267\272\000\000e #clojure
;; d8:completei1e10:downloadedi2e10:incompletei1e8:intervali1749e12:min intervali874e5:peers12:J\324\267\272\000\000`~h\333\3529e #ruby
