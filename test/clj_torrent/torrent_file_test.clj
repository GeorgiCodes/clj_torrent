(ns clj-torrent.torrent-file-test
  (:use [midje.sweet])
  (:require [clj-torrent.torrent-file :refer :all]))

(fact "about percent encoding"
;;   (parse-peers "Bl\xAD\x10\x00\x00`~h\xDB\xEA9") => ["66.108.173.16:0" "96.126.104.219:59961"])
      (parse-peers  => ["66.108.173.16:0" "96.126.104.219:59961"]))
