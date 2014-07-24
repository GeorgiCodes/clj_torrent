(ns clj-torrent.metadata
  (:require [bencode.bencode :as bencode]))

(defn read-torrent-file
  [filename]
  (let [file-contents (slurp filename :encoding "ISO-8859-1")]
    file-contents))

(defn init-metadata
  [filename]
  (let [metadata (bencode/decode (read-torrent-file filename))
        info (get metadata "info")]
    {:announce (get metadata "announce")
     :created_by (get metadata "created by")
     :creation_date (get metadata "creation date")
     :encoding (get metadata "encoding")
     :info (bencode/encode "info")
     :length (get info "length")
     :files (get info "name")
     :piece_length (get info "piece length")
     :pieces (get info "pieces")}))



(init-metadata "examples/flagfromserver.torrent")

