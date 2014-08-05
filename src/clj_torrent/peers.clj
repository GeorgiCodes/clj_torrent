(ns clj-torrent.peers
  (:require [clojure.core.async :as async :refer :all :exclude [map into reduce merge partition partition-by take]]
            [org.httpkit.client :as http]))

(http/get "96.126.104.219:59961"
          (fn [{:keys [status headers body error]}] ;; asynchronous handle response
            (if error
              (println "Failed, exception is " error)
              (println "Async HTTP GET: " status))))
