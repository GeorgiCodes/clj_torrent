(ns clj-torrent.utils)

(defn hexify [coll]
  "Convert byte sequence to hex string"
  (let [hex [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \a \b \c \d \e \f]]
    (letfn [(hexify-byte [b]
                         (let [v (bit-and b 0xFF)]
                           [(hex (bit-shift-right v 4)) (hex (bit-and v 0x0F))]))]
      (apply str (mapcat hexify-byte coll)))))

(defn hexify-str [s]
  "Convert string sequence to hex string"
  (hexify (.getBytes s)))

(defn percent-encode[s]
  "Takes in a string and percent encodes it"
  (clojure.string/join "" (into ["%"] (map-indexed (fn [idx itm] (if (and (odd? idx) (not= idx (dec (count s)))) (str itm "%") itm)) s))))
