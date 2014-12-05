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
                         cosine-distance 1)
           [{"1" 0.44529980377477085}]))
    (is (= (score-labels [(struct observation "1" [1 2])
                         (struct observation "1" [2 3])
                         (struct observation "-1" [9 3])]
                         [(struct observation "1" [9 0])]
                         cosine-distance 3)
           [{"-1" 0.05131670194948623, "1" 0.998086208274813}]))
    (is (= (score-labels [(struct observation "1" [1 2])
                         (struct observation "1" [2 3])
                         (struct observation "-1" [2 3])]
                         [(struct observation "1" [9 0])]
                         cosine-distance 3 :score-modifier (partial - 1))
            [{"-1" 0.5547001962252291, "1" 1.001913791725187}]))))
