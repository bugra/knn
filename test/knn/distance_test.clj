(ns knn.distance-test
  (:require [clojure.test :refer :all]
            [knn.distance :refer :all]
            [knn.op :as op]))

; Tests are adapted from:
; https://github.com/JuliaStats/Distance.jl/blob/master/test/test_dists.jl

(def first-vector [4. 5. 6. 7.])
(def second-vector [3. 9. 8. 1.])

(def third-vector [1. 2. 1. 3. 2. 1.])
(def fourth-vector [1. 3. 0. 2. 2. 0.])

(defn round
  "Round double/float numbers upto 2 decimal places"
  [number]
  (double (/ (Math/round (* number 100)) 100)))

(deftest manhattan-distance-test
  (testing "Distance: Manhattan Distance Test"
    (is (= 13.0 (manhattan-distance first-vector second-vector)))
    (is (= 0.0 (manhattan-distance first-vector first-vector)))
    (is (= 0.0 (manhattan-distance second-vector second-vector)))))

(deftest euclidean-distance-test
  (testing "Distance: Euclidean Distance Test"
    (is (= (Math/sqrt 57) (euclidean-distance first-vector second-vector)))
    (is (= 0. (euclidean-distance first-vector first-vector)))
    (is (= 0. (euclidean-distance second-vector second-vector)))))

(deftest squared-euclidean-distance-test
  (testing "Distance: Squared Euclidean Distance Test"
    (is (= (squared-euclidean-distance first-vector second-vector)
           (* (euclidean-distance first-vector second-vector) (euclidean-distance first-vector second-vector))))))


(deftest minkowski-distance-test
  (testing "Distance: Minkowski Distance Test"
    (is (= 0.0 (minkowski-distance first-vector first-vector 1)))
    (is (= 0.0 (minkowski-distance first-vector first-vector 3)))
    (is (= 0.0 (minkowski-distance first-vector first-vector 100)))
    (is (= (manhattan-distance first-vector second-vector) (minkowski-distance first-vector second-vector 1)))
    (is (= (euclidean-distance first-vector second-vector) (minkowski-distance first-vector second-vector 2)))))

(deftest weighted-minkowski-distance-test
  (testing "Distance: Weighted Minkowski Distance Test"
    (is (= 0.0 (weighted-minkowski-distance first-vector first-vector first-vector 1)))
    (is (= 0.0 (weighted-minkowski-distance second-vector second-vector first-vector 1)))))

(deftest chebyshev-distance-test
  (testing "Distance: Chebyshev Distance Test"
    (is (= 6.0 (chebyshev-distance first-vector second-vector)))))

(deftest hamming-distance-test
  (testing "Distance: Hamming Distance Test"
    (is (= 0 (hamming-distance third-vector third-vector)))
    (is (= 0 (hamming-distance fourth-vector fourth-vector)))
    (is (= 4 (hamming-distance third-vector fourth-vector)))))

(deftest bray-curtis-distance-test
  (testing "Distance: Bray Curtis Distance Test"
    (is (= 0.0 (bray-curtis-distance first-vector first-vector)))
    (is (= 0.0 (bray-curtis-distance second-vector second-vector)))
    (is (= 0.3023255813953488 (bray-curtis-distance first-vector second-vector)))))

(deftest canberra-distance-test
  (testing "Distance: Canberra Distance Test"
    (is (= 0.0 (canberra-distance first-vector first-vector)))
    (is (= 0.0 (canberra-distance second-vector second-vector)))
    (is (= 1.3214285714285714 (canberra-distance first-vector second-vector)))))

(deftest cosine-distance-test
  (testing "Distance: Cosine Distance Test"
    (is (= 0.0 (cosine-distance first-vector first-vector)))
    (is (= 0.0 (cosine-distance second-vector second-vector)))
    (is (= (- 1.0 (/ 112. (Math/sqrt 19530.))) (cosine-distance first-vector second-vector)))))

(deftest correlation-distance-test
  (testing "Distance: Correlation Distance Test"
    (is (= 0.0 (round (correlation-distance first-vector first-vector))))
    (is (= 0.0 (correlation-distance second-vector second-vector)))
    (is (= (correlation-distance first-vector second-vector)
           (cosine-distance (op/scalar-subtract first-vector (op/mean first-vector))
                            (op/scalar-subtract second-vector (op/mean second-vector)))))))

(deftest chi-squared-distance-test
  (testing "Distance: Chi-squared distance test"
    (is (= 0.0 (chi-squared-distance first-vector first-vector)))
    (is (= 0.0 (chi-squared-distance second-vector second-vector)))
    (is (= 6.071428571428571 (chi-squared-distance first-vector second-vector)))))

(deftest kl-divergence-test
  (testing "Distance: KL Divergence Test"
    (is (= 0.0 (kl-divergence first-vector first-vector)))
    (is (= 0.0 (kl-divergence second-vector second-vector)))
    (is (= 10.107073573973036 (kl-divergence first-vector second-vector)))))

(deftest js-divergence-test
  (testing "Distance: JS Divergence Test"
    (is (= 0.0 (js-divergence first-vector first-vector)))
    (is (= 0.0 (js-divergence second-vector second-vector)))
    (is (= 3.3255054188991044 (js-divergence first-vector second-vector)))))

(deftest span-norm-distance-test
  (testing "Distance: Span-norm distance test"
    (is (= 0.0 (span-norm-distance first-vector first-vector)))
    (is (= 0.0 (span-norm-distance second-vector second-vector)))
    (is (= 10.0 (span-norm-distance first-vector second-vector)))))

(deftest bhattacharyya-distance-test
  (testing "Distance: Bhattacharyya Distance Test"
    (is (= 0.0 (bhattacharyya-distance first-vector first-vector)))
    (is (= 0.0 (bhattacharyya-distance second-vector second-vector)))
    (is (= 0.08481833470852809 (bhattacharyya-distance first-vector second-vector)))))

(deftest hellinger-distance-test
  (testing "Distance: Hellinger Distance Test"
    (is (= 0.0 (hellinger-distance first-vector first-vector)))
    (is (= 0.0 (hellinger-distance second-vector second-vector)))
    (is (= 0.2851680883957031 (hellinger-distance first-vector second-vector)))))
