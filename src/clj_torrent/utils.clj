(ns clj-torrent.utils
  (:import (java.security MessageDigest)))

(defn bytes? [x]
  (= (Class/forName "[B")
     (.getClass x)))

(defn hex-from-bytes
  "Converts a byte-array to a hex string."
  [byte-arr]
  (let [sb (StringBuffer.)]
    (doseq [b byte-arr]
      (.append sb (.substring (Integer/toString (+ (bit-and b 0xff) 0x100) 16) 1)))
    (.toString sb)))

(defn stringify-bytes [bytes] (->> bytes (map (partial format "%02x")) (apply str)))

;; (defn sha1-hash [data]
;;   "Takes a string or byte array and returns it's SHA1 hash"
;;   (let [data (if (bytes? data)
;;                (.getBytes data))]
;;   (.digest (MessageDigest/getInstance sha1) data)))

