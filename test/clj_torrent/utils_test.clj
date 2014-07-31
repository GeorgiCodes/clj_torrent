(ns clj-torrent.utils-test
  (:use [midje.sweet])
  (:require [clj-torrent.utils :refer :all]
            [clj-torrent.metadata :refer :all]
            [clj-torrent.torrent-file :refer :all]))

(fact "about hexify"
      (let [sml-torrent (init-metadata "examples/flagfromserver.torrent")]
      (hexify (:info-hash sml-torrent)) => "2b15ca2bfd48cdd76d39ec55a3ab1b8a57180a09"))

(fact "about percent encoding"
  (percent-encode "2b15ca2bfd48cdd76d39ec55a3ab1b8a57180a09")  => "%2B%15%CA%2B%FDH%CD%D7m9%ECU%A3%AB%1B%8AW%18%0A%09")
