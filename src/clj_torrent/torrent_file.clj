(ns clj-torrent.torrent-file
  (:import (java.security MessageDigest)
           (java.util Random)
           (java.util UUID)
           [java.net URLEncoder]))

(def peer-id-length 20)
;; (def peer-prefix "GK")

(defn generate-peer-id []
  (take peer-id-length (.toString (UUID/randomUUID))))
;; (defn generate-peer-id []
;;   (let [rando (Random.)
;;         random-byte-array (byte-array peer-id-length)]
;;     (. rando nextBytes random-byte-array)
;;     (random-byte-array)))

;; (URLEncoder/encode "123 aslk")
(URLEncoder/encode "GK121212121212121212")

(generate-peer-id)

(.toString (UUID/randomUUID))
