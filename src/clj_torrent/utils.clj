(ns clj-torrent.utils
  (:import (java.security MessageDigest)))

(defn sha1-hash [data]
  (println (class data))
  (.digest (MessageDigest/getInstance "SHA1") data))

(sha1-hash "1")
