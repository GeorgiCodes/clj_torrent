(defproject clj_torrent "0.1.0-SNAPSHOT"
  :description "A BitTorrent Client written by @GeorgiCodes"
  :url "https://github.com/GeorgiCodes"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.zachallaun/bencode "0.1.1-SNAPSHOT"]
                 [bencode "0.2.5"]
                 [http-kit "2.1.16"]
                 [ring/ring-codec "1.0.0"]
                 [pandect "0.3.4"]
                 [org.clojure/tools.logging "0.3.0"]
                 [log4j/log4j "1.2.17"]]
  :main ^:skip-aot clj-torrent.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
