(ns clj-torrent.client
  (:import [java.net URLEncoder])
  (:use clj-torrent.torrent-file)
  (:require [clj-torrent.metadata :as metadata]
            [org.httpkit.client :as http]
            [clojure.tools.logging :as log]))

(defn parse-torrent-files [filenames]
  (map #(metadata/init-metadata %) filenames))

(defn connect-to-tracker [metadata]
  (log/info "Connecting to tracker" (:announce metadata))
;;   (log/info "peer-id" (generate-peer-id))
;;   (log/info "info-hash" (:info-hash metadata))
;;   (log/info "info-hash class" (class (:info-hash metadata)))
  (let [{:keys [status headers body error] :as resp}
        @(http/get (:announce metadata) {:query-params {"info_hash" "\xab"
                                                        "peer_id" "GK121212121212121212"
                                                        "left" (:length metadata)}})]
    (if error
      (log/error "Failed, exception: " error)
      (log/info "HTTP GET success: " body))))

;; (URLEncoder/encode (:info-hash metadata))

(defn connect-to-trackers [metadata-list]
  (log/info "Connecting to" (count metadata-list) "trackers")
  (connect-to-tracker (first metadata-list))
  ;;   (map #(other) metadata-list)
  )

(defn start! [filenames]
  (let [metadata-list (parse-torrent-files filenames)]
    (connect-to-trackers metadata-list)))


;; .testing

;; @(http/get (:announce metadata) {:query-params {"info_hash" (:info-hash metadata)
;;                                                         "peer_id" (generate-peer-id)
;;                                                         "left" (:length metadata)}})
;; GET /announce?info_hash=%25EF%25BF%25BD9%25EF%25BF%25BD%25EF%25BF%25BD%255EkK%250D2U%25EF%25BF%25BD%25EF%25BF%25BD%2560%2518%25EF%25BF%25BD%25EF%25BF%25BD%25EF%25BF%25BD%2507%2509&peer_id=GK121212121212121212&left=1277987 HTTP/1.1\r\n
;; GET /announce?info_hash=%2B%15%CA%2B%FDH%CD%D7m9%ECU%A3%AB%1B%8AW%18%0A%09&peer_id=GK-PLnhy0ZgvgLLnmecI&left=1277987 HTTP/1.1\r\n
;; GET /announce?info_hash=%252B%2515%25CA%252B%25FDH%25CD%25D7m9%25ECU%25A3%25AB%251B%258AW%2518%250A%2509&peer_id=GK121212121212121212&left=1277987 HTTP/1.1\r\n
