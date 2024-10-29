(ns Task1)

(defn is-correct-word? [word]
  (not= (last word) (nth word (- (count word) 2))))

(defn extend-words [words alphabet]
  (->> alphabet
       (mapcat (fn [letter] (map #(str % letter) words)))
       (filter is-correct-word?)))

(defn create-language [n alphabet]
  (if (<= n 0)
    '()
    (reduce (fn [words _] (extend-words words alphabet))
            alphabet
            (range (dec n)))))
