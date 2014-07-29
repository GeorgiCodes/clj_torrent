(ns clj-torrent.utils)

(defn bytes? [x]
  (= (Class/forName "[B")
     (.getClass x)))
