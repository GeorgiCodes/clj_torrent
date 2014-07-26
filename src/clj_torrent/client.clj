(ns clj-torrent.client
  (:require [clj-torrent.metadata :as metadata]))

(defn parse-torrent-files [filenames]
  (map #(metadata/init-metadata %) filenames))

(defn start! [filenames]
  (let [metadata-list (parse-torrent-files filenames)]
    (println metadata-list)))

