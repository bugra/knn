(ns knn.core-test
  (:require [clojure.test :refer :all]
            [knn.core :refer :all]
            [knn.distance :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest test-knn
  (testing "Tests weighted version of kNN."
    (is (= (score-labels [(struct observation "1" [1 2])
                         (struct observation "1" [2 3])
                         (struct observation "-1" [2 3])]
                         [(struct observation "1" [9 0])]
                         euclidean-distance 1)
           [{"1" 7.615773105863909}]))
    (is (= (score-labels [(struct observation "1" [1 2])
                         (struct observation "1" [2 3])
                         (struct observation "-1" [2 3])]
                         [(struct observation "1" [9 0])]
                         euclidean-distance 3)
           [{"-1" 7.615773105863909, "1" 15.86198435709923}]))
    (is (= (score-labels [(struct observation "1" [1 2])
                         (struct observation "1" [2 3])
                         (struct observation "-1" [2 3])]
                         [(struct observation "1" [9 0])]
                         cosine-distance 3 :score-modifier (partial - 1))
            [{"-1" 0.5547001962252291, "1" 1.001913791725187}]))))
