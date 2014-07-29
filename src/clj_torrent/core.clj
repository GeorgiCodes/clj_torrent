(ns clj-torrent.core
  (:gen-class)
  (:use clj-torrent.torrent-file)
  (:require [clojure.tools.logging :as log]))

(defn -main
  "Takes in a list of filenames and starts downloading via the BitTorrent protocol"
  [& args]
  (let [filenames ["examples/flagfromserver.torrent"]]
    (log/info "Hello, welcome to Georgi's awesome Clojure BitTorrent client")
    (start! (set filenames))))
