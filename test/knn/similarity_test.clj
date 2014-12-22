(ns knn.similarity-test
  (:require [clojure.test :refer :all]
            [knn.similarity :refer :all]
            [knn.op :as op]))

; Tests are adapted from:
; https://github.com/JuliaStats/Distance.jl/blob/master/test/test_dists.jl

(def zero-vector [0. 0. 0. 0.])
(def first-vector [4. 5. 6. 7.])
(def second-vector [3. 9. 8. 1.])

(defn round
  "Round double/float numbers upto 2 decimal places"
  [number]
  (double (/ (Math/round (* number 100)) 100)))

(deftest cosine-similarity-test
  (testing "Similarity: Cosine Similarity Test"
    (is (= 0.8 (round (cosine-similarity first-vector second-vector))))
    (is (= 1.0 (cosine-similarity first-vector first-vector)))
    (is (= 1.0 (cosine-similarity second-vector second-vector))))
    (is (thrown? ArithmeticException (cosine-similarity zero-vector first-vector))))
