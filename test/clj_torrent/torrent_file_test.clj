(ns clj-torrent.torrent-file-test
  (:use [midje.sweet])
  (:require [clj-torrent.torrent-file :refer :all]))

(fact "about peer parsing"
;;   (parse-peers "Bl\xAD\x10\x00\x00`~h\xDB\xEA9") => ["66.108.173.16:0" "96.126.104.219:59961"])
      (parse-peers  => ["66.108.173.16:0" "96.126.104.219:59961"]))

(def x (byte-array [(byte 0x43)

                           (byte 0x6c)

                           (byte 0x6f)

                           (byte 0x6a)

                           (byte 0x75)

                           (byte 0x72)

                           (byte 0x65)

                           (byte 0x21)]))
