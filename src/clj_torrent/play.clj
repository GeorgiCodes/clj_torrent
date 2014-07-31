(ns clj-torrent.play
  (:import (java.util UUID)
           [java.net URLEncoder])
  (:use clj-torrent.torrent-file
        clj-torrent.metadata
        clj-torrent.utils))



;; %2B%15%CA%2B%FDH%CD%D7m9%ECU%A3%AB%1B%8AW%18%0A%09

;; (hexify (:info-hash sml-torrent))


;; (start! ["examples/flagfromserver.torrent"])
;; (def sml-torrent (init-metadata "examples/flagfromserver.torrent"))
;; (:info-hash sml-torrent)
;; (first (:info-hash sml-torrent))
;; (:info-hash sml-torrent)
;; (println (:info-bencode sml-torrent))
;; (String. (:info-hash sml-torrent))

;; (URLEncoder/encode (hexify (:info-hash sml-torrent)))
;; (URLEncoder/encode (hexify (:info-hash sml-torrent)) "ISO-8859-1")
;; (sha1-info-hash sml-torrent)

;; (percent-encode "2b15ca2bfd48cdd76d39ec55a3ab1b8a57180a09")

