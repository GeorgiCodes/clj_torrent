(ns clj-torrent.client
  (:import [java.net URLEncoder])
  (:use clj-torrent.torrent-file)
  (:require [clj-torrent.metadata :as metadata]
            [org.httpkit.client :as http]
            [clojure.tools.logging :as log]))

(defn parse-torrent-files [filenames]
  (map #(metadata/init-metadata %) filenames))

(defn init-tracker-url [metadata]
  "Constructs a URL to connect to a tracker with correct encoding of info_hash"
  (str (:announce metadata) "?info_hash=" (URLEncoder/encode (String. (:info-hash metadata)) "ISO-8859-1")
       "&peer_id=" "GK121212121212121212" "&left=" (:length metadata)))

(defn connect-to-tracker [metadata]
  (log/info "Connecting to tracker" (init-tracker-url metadata))
  (let [{:keys [status headers body error] :as resp}
        @(http/get (init-tracker-url metadata))]
    (if error
      (log/error "Failed, exception: " error)
      (log/info "HTTP GET success: " body))))

(defn connect-to-trackers [metadata-list]
  (log/info "Connecting to" (count metadata-list) "trackers")
  (connect-to-tracker (first metadata-list)))

(defn start! [filenames]
  (let [metadata-list (parse-torrent-files filenames)]
    (connect-to-trackers metadata-list)))
