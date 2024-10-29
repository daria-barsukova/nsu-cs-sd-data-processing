(ns Task2-test
  (:require [clojure.test :refer [deftest is testing]]
            [Task2 :refer [primes]]))

(deftest test1
  (testing "First 10 primes"
    (is (= (take 10 (primes)) [2 3 5 7 11 13 17 19 23 29]))))

(deftest test2
  (testing "Checking a few prime numbers"
    (is (some #{7} (take 10 (primes))))
    (is (some #{13} (take 20 (primes))))))

(deftest test3
  (testing "Checking values of prime numbers by index" 
    (is (= (nth (primes) 10) 31))
    (is (= (nth (primes) 100) 547))
    (is (= (nth (primes) 1000) 7927))
    (is (= (nth (primes) 10000) 104743))))
