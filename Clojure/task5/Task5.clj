(ns Task5)

(def num-philosophers 5)
(def think-time-ms 1000)
(def dining-time-ms 1000)
(def num-periods 10)

(def transaction-retries (atom 0))

(defn create-forks []
  (vec (map (fn [n] {:fork (ref 0), :id n})
            (range num-philosophers))))

(def forks (create-forks))

(defn philosopher [id left-fork right-fork]
  (Thread.
    (fn []
      (dotimes [_ num-periods]
        (println (str "Philosopher " id " is thinking. "))
        (Thread/sleep think-time-ms)

        (println (str "Philosopher " id " is preparing to eat. "))
        (dosync
          (println (str "Philosopher " id " starting transaction. "))
          (println (str "Philosopher " id " attempting to lock fork " (:id left-fork) " "))
          (swap! transaction-retries inc)
          (alter (:fork left-fork) inc)
          (println (str "Philosopher " id " attempting to lock fork " (:id right-fork) " "))
          (swap! transaction-retries inc)
          (alter (:fork right-fork) inc)

          (println (str "Philosopher " id " is eating. "))
          (Thread/sleep dining-time-ms))
        
        (println (str "Philosopher " id " finished eating. "))))))

(defn create-philosophers []
  (map (fn [id]
         (philosopher id
                      (nth forks id)
                      (nth forks (mod (+ id 1) num-philosophers))))
       (range num-philosophers)))

(def philosophers (create-philosophers))

(defn run-philosophers []
  (doall (map #(.start %) philosophers)))

(defn wait-for-philosophers []
  (doall (map #(.join %) philosophers)))

(defn -main []
  (time
    (do
      (run-philosophers)
      (wait-for-philosophers))) 
  
  (let [successful-transactions (reduce + (map #(deref (:fork %)) forks))]
    (println (str "Successful transactions: " successful-transactions))
    (println (str "Total transaction attempts: " @transaction-retries))
    (println (str "Failed transactions: " (- @transaction-retries successful-transactions)))))

(-main)
