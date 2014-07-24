(ns clj-torrent.metadata
  (:require [bencode.bencode :as bencode]))

(defn read-torrent-file
  [filename]
  (let [file-contents (slurp filename :encoding "ISO-8859-1")]
    file-contents))

(defn init-length [info]
  (let [files (get info "files")]
    (if (empty? files)
      (get info "length")
      (reduce (fn [accum file] (+ accum (get file "length"))) 0 files))))

(defn init-files [info]
  (let [files (get info "files")]
    (if (empty? files)
      (conj [] (get info "name"))
      (into [] (map (fn [file] (clojure.string/join "/" (get file "path"))) files)))))

(defn init-metadata
  [filename]
  (let [metadata (bencode/decode (read-torrent-file filename))
        info (get metadata "info")]
    {:announce (get metadata "announce")
     :created_by (get metadata "created by")
     :creation_date (get metadata "creation date")
     :encoding (get metadata "encoding")
     :info (bencode/encode "info")
     :length (init-length info)
     :files (init-files info)
     :piece_length (get info "piece length")
     :pieces (get info "pieces")}))


;; testing
(def sml-torrent (init-metadata "examples/flagfromserver.torrent"))
(:files sml-torrent)

(init-metadata "examples/karl_marx.torrent")

(def files [{"length" 291 "path" ["file" "path" "one.txt"]} {"length" 100 "path" ["file" "path" "two.txt"]}])
(first files)
(into [] (map (fn [file] (clojure.string/join "/" (get file "path"))) files))
(reduce (fn [accum file] (+ accum (get file "length"))) 0 files)
