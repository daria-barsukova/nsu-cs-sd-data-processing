(ns Task3-test
  (:require [clojure.test :refer [deftest is testing]]
            [Task3 :refer [pfilter]]))

(defn slow-even? [n]
  (Thread/sleep 10)
  (even? n))

(deftest test1
  (testing "Точность параллельной фильтрации"
    (is (= (filter odd? (range 0)) (pfilter odd? (range 0))))
    (is (= (filter even? (range 100)) (pfilter even? (range 100))))
    (is (= (filter even? (range 1000)) (pfilter even? (range 1000))))))

(deftest pfilter-performance-test
  (testing "Сравнение производительности"
    (println "\nВремя выполнения filter:")
    (time (doall (take 100 (filter slow-even? (iterate inc 0)))))

    (println "\nВремя выполнения pfilter:")
    (time (doall (take 100 (pfilter slow-even? (iterate inc 0)))))))