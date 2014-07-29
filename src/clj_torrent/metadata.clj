(ns clj-torrent.metadata
  (:import [java.security MessageDigest])
  (:require [bencode.bencode :refer [decode encode]]
            [clojure.tools.logging :as log]
            [bencode.core :refer [bencode]]
            [bencode.metainfo.reader :refer [torrent-info-hash parse-metainfo-file]]))

(defn- init-length [info]
  (let [files (get info "files")]
    (if (empty? files)
      (get info "length")
      (reduce (fn [accum file] (+ accum (get file "length"))) 0 files))))

(defn- init-files [info]
  (let [files (get info "files")]
    (if (empty? files)
      (conj [] (get info "name"))
      (into [] (map (fn [file] (clojure.string/join "/" (get file "path"))) files)))))

(defn init-metadata
  [filename]
  (let [metadata (parse-metainfo-file filename)
        info (get metadata "info")
        info-bencode (bencode info {:raw-str? true})]
    {:announce (get metadata "announce")
     :created_by (get metadata "created by")
     :creation_date (get metadata "creation date")
     :encoding (get metadata "encoding")
     :info info
     :info-bencode info-bencode
     :info-hash (torrent-info-hash metadata)
     :length (init-length info)
     :files (init-files info)
     :piece_length (get info "piece length")
     :pieces (get info "pieces")}))
