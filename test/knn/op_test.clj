(ns knn.op-test
  (:require [clojure.test :refer :all]
            [knn.op :refer :all]))

;; Need to fix decimal place problem
;; And Find out dot product problem

(def first-vector [2.4 42.0 0.0 23.2 4.5])
(def second-vector [3.7 7.8 4.9 7.1 13.3])
(def third-vector [-2.2 -3.3 4.7 5.9 -6.1])

(defn round
  "Round double/float numbers upto 2 decimal places"
  [number]
  (double (/ (Math/round (* number 100)) 100)))

(deftest add-test
  (testing "Vector addition test"
    (let [actual-vector [6.1 49.8 4.9 30.3 17.8]]
    (is (= actual-vector (map round (add first-vector second-vector)))))))

(deftest subtract-test
  (testing "Vector subtraction test"
    (let [actual-vector [-1.3 34.2 -4.9 16.1 -8.8]]
      (is (= actual-vector (map round (subtract first-vector second-vector)))))))

(deftest mult-test
  (testing "Vector multiplication test"
    (let [actual-vector [8.88 327.6 0. 164.72  59.85]]
      (is (= actual-vector (map round (mult first-vector second-vector)))))))

(deftest div-test
  (testing "Vector division test"
    (let [actual-vector [0.6486486486486486 5.384615384615385 0. 3.267605633802817 0.3383458646616541]]
      (is (= actual-vector (div first-vector second-vector))))))

(deftest dot-test
  (testing "Vector Dot Product Test"
    (is (= 561.05 (dot first-vector second-vector)))))

(deftest scalar-mult-test
  (testing "Scalar multiplication with Vector Test"
    (let [actual-vector [12.0 210.0 0.0 116.0 22.5]]
     (is (= actual-vector (scalar-mult first-vector 5))))))

(deftest scalar-div-test
  (testing "Scalar divisiton with Vector Test"
    (let [actual-vector [1.2 21.0 0.0 11.6 2.25]]
      (is (= actual-vector (scalar-div first-vector 2))))))

(deftest clamp-test
  (testing "Clamp for Vector Test"
    (let [actual-vector [2.4 11.0 1.0 11.0 4.5]]
      (is (= actual-vector (clamp 1.0 11.0 first-vector))))))

(deftest mean-test
  (testing "Mean of a Vector Test"
    (is (= 14.42 (round (mean first-vector))))))

(deftest moving-average-test
  (testing "Moving Average of A Vector Test"
    (let [actual-vector [16.9 17.425]]
      (is (= actual-vector (moving-average first-vector 4))))))

(deftest squared-test
  (testing "Squared of a Vector Test"
    (let [actual-vector [5.76 1764.0 0. 538.24 20.25]]
      (is (= actual-vector (squared first-vector))))))

(deftest abs-test
  (testing "Operation: Absolute of a Vector Test"
    (let [actual-vector [2.2 3.3 4.7 5.9 6.1]]
      (is (= actual-vector (abs third-vector))))))

(deftest pow-test
  (testing "Operation: Power of a Vector Test"
    (is (= [-2.2 -3.3 4.7 5.9 -6.1] (pow third-vector 1)))
    (is (= [1.0 1.0 1.0 1.0 1.0] (pow third-vector 0)))
    (is (= [4.84 10.89 22.09 34.81 37.21] (map round (pow third-vector 2))))))
