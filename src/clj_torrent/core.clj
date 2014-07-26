(ns clj-torrent.core
  (:gen-class)
  (:require [clj-torrent.client :as client]
            [clojure.tools.logging :as log]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [filenames ["examples/flagfromserver.torrent" "examples/karl_marx.torrent"]]
    (log/info "Hello, welcome to Georgi's awesome Clojure BitTorrent client")
    (client/start! (set filenames))))
