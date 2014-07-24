(ns clj-torrent.core
  (:gen-class)
  (:use clj-torrent.metadata))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (init-metadata "examples/flagfromserver.torrent"))


