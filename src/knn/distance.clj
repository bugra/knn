(ns knn.distance)
(use '[knn.op :as op])
(use '[knn.norm :as norm])
(use '[knn.similarity :as similarity])


(defn- bray-curtis-distance-denominator
  "Util Function to be used to compute Bray Curtis Distance
  Function between two vectors"
  [first-vector second-vector]
  (reduce + (map #(Math/abs (+ %1 %2)) first-vector second-vector)))

(defn manhattan-distance
  "Manhattan Distance between two vectors, called l_1 distance as well\n
  Formula: sum(abs(x - y))"
  [first-vector second-vector]
  (reduce + (map #(Math/abs (- %1 %2)) first-vector second-vector)))

(defn euclidean-distance
  "Euclidean distance between two vectors\n
  Formula: sqrt(sum((x - y) .^ 2))"
  [first-vector second-vector]
  (Math/sqrt (reduce + (map #(Math/pow (- %1 %2) 2) first-vector second-vector))))

(defn squared-euclidean-distance
  "Squared Euclidean Distance between two vectors\n
  Formula: sum((x - y) .^ 2)"
  [first-vector second-vector]
  (* (euclidean-distance first-vector second-vector) (euclidean-distance first-vector second-vector)))

(defn minkowski-distance
  "Minkowski Distance, known as l-p distance
  Most Generic form of l-p distances, covers l_1(manhattan)
  and l_2(euclidean distances) as well\n
  Formula: sum(abs(x - y).^p) ^ (1/p)"
  [first-vector second-vector p]
  (Math/pow (reduce + (map #(Math/pow (Math/abs (- %1 %2)) p) first-vector second-vector)) (/ p)))

(defn weighted-minkowski-distance
  "Weighted Minkowski Distance, with different weights
  on the difference between observations\n
  Formula: sum(abs(x - y).^p .* w) ^ (1/p)"
  [first-vector second-vector weight-vector p]
  (Math/pow
   (reduce + (map #(* (Math/pow (Math/abs (- %1 %2)) p) %3) first-vector second-vector weight-vector))
   (/ p)))

(defn chebyshev-distance
  "Chebyshev Distance
  It is a form of l_p distance when p -> /Infinity\n
  Formula: max(abs(x - y))"
  [first-vector second-vector]
  (apply max (map #(Math/abs (- %1 %2)) first-vector second-vector)))

(defn hamming-distance
  "Hamming Distance between two vectors\n
  Formula: sum(x .!= y)"
  [first-vector second-vector]
  (count
   (filter true? (map #(not= %1 %2) first-vector second-vector))))

(defn bray-curtis-distance
  "Bray Curtis Distance Function"
  [first-vector second-vector]
  (/ (manhattan-distance first-vector second-vector) (bray-curtis-distance-denominator first-vector second-vector)))

(defn canberra-distance
  "Canberra Distance"
  [first-vector second-vector]
  (reduce + (map #(/ (Math/abs (- %1 %2)) (+ (Math/abs %1) (Math/abs %2))) first-vector second-vector)))

(defn cosine-distance
  "Cosine distance between two vectors\n
  Formula: 1 - dot(x, y) / (norm(x) * norm(y))"
  [first-vector second-vector]
  (- 1 (similarity/cosine-similarity first-vector second-vector)))

(defn correlation-distance
  "Correlation distance between two vectors\n
  Formula: cosine-distance(x - mean(x), y - mean(y))"
  [first-vector second-vector]
  (cosine-distance (op/scalar-subtract first-vector (op/mean first-vector))
                   (op/scalar-subtract second-vector (op/mean second-vector))))

(defn chi-squared-distance
  "Chi Squared Distance between two vectors\n
  Formula: sum((x - y).^2 / (x + y))"
  [first-vector second-vector]
  (reduce + (map #(/ (* (- %1 %2) (- %1 %2) ) (+ %1 %2)) first-vector second-vector)))

(defn- kl-div
  "Kullback-Leibler Divergence of two vectors with a function
  that is mapped to the ratio"
  [first-vector second-vector func]
  (reduce + (op/mult first-vector (map func (op/div first-vector second-vector)))))

(defn kl-divergence
  "Kullback-Leibler Divergence with a normal Log function\n
  Formula: sum(p .* log(p ./ q))"
  [first-vector second-vector]
  (kl-div first-vector second-vector #(Math/log %)))

(defn js-divergence
  "JS Divergence of two vectors\n
  Formula: KL(x, m) / 2 + KL(y, m) / 2 where
  m = (x + y) / 2"
  [first-vector second-vector]
  (let [m (op/scalar-div (op/add first-vector second-vector) 2)]
    (+ (kl-divergence first-vector m) (kl-divergence second-vector m))))

(defn span-norm-distance
  "Span-norm distance between two vectors\n
  Formula: max(x - y) - min(x - y)"
  [first-vector second-vector]
  (- (apply max (op/subtract first-vector second-vector)) (apply min (op/subtract first-vector second-vector))))

(defn bhattacharyya-distance
  "Bhattacharyya Distance between two vectors\n
  Formula: -log(sum(sqrt(x .* y) / sqrt(sum(x) * sum(y)))"
  [first-vector second-vector]
  (- (Math/log
      (reduce + (map #(/ (Math/sqrt (* %1 %2)) (Math/pow (* (reduce + first-vector)
                                                             (reduce + second-vector)) 0.5)) first-vector second-vector)))))

(defn hellinger-distance
  [first-vector second-vector]
  (Math/pow (- 1.0 (reduce + (map #(/ (Math/sqrt (* %1 %2)) (Math/pow (* (reduce + first-vector)
                                                             (reduce + second-vector)) 0.5)) first-vector second-vector))) 0.5))
