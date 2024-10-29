(ns Task2)

(defn multiple-of? [p]
  (fn [x] (zero? (mod x p))))

(defn primes []
  (lazy-seq
   (letfn [(sieve [s]
             (cons (first s)
                   (lazy-seq (sieve (remove (multiple-of? (first s)) (rest s))))))]
     (sieve (iterate inc 2)))))
