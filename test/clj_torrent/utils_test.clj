(ns clj-torrent.utils-test
  (:use [midje.sweet])
  (:require [clj-torrent.utils :refer :all]
            [clj-torrent.metadata :refer :all]
            [clj-torrent.torrent-file :refer :all]))

(fact "about hexify"
      (let [sml-torrent (init-metadata "examples/flagfromserver.torrent")]
      (hexify (:info-hash sml-torrent)) => "2b15ca2bfd48cdd76d39ec55a3ab1b8a57180a09"))

(fact "about percent encoding"
  (percent-encode "2b15ca2bfd48cdd76d39ec55a3ab1b8a57180a09")  => "%2b%15%ca%2b%fd%48%cd%d7%6d%39%ec%55%a3%ab%1b%8a%57%18%0a%09")
