(ns knn.norm-test
  (:require [clojure.test :refer :all]
            [knn.norm :refer :all]))


(def first-vector [3.0 -4.0])

(defn round
  "Round double/float numbers upto 2 decimal places"
  [number]
  (double (/ (Math/round (* number 100)) 100)))

(deftest manhattan-norm-test
  (testing "Norm: Manhattan Norm Test"
    (is (= 7.0 (manhattan-norm first-vector)))))

(deftest euclidean-norm-test
  (testing "Norm: Euclidean Norm Test"
    (is (= 5.0 (euclidean-norm first-vector)))))

(deftest lp-norm-test
  (testing "Norm: L-p Norm Test"
    (is (= (manhattan-norm first-vector) (lp-norm first-vector 1)))
    (is (= (euclidean-norm first-vector) (lp-norm first-vector 2)))
    (is (= 4.497941445275415 (lp-norm first-vector 3)))
    (is (= 4.284572294953817 (lp-norm first-vector 4)))
    (is (= (max-norm first-vector) (round (lp-norm first-vector 100))))))

(deftest max-norm-test
  (testing "Norm: Max Norm Test"
    (is (= 4.0 (round (max-norm first-vector))))))

