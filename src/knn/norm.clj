(ns knn.norm)

(use '[knn.op :as op])

(defn manhattan-norm
  "Manhattan norm of a vector
  l_1 norm"
  [v]
  (reduce + (map #(Math/abs %) v)))

(defn euclidean-norm
  "Euclidean norm of a vector
  l_2 norm"
  [v]
  (Math/sqrt (op/dot v v)))

(defn lp-norm
  "L-p Norm of a vector
  l_p norm"
  [v p]
  (Math/pow (reduce + (map #(Math/pow (Math/abs %) p) v)) (/ p)))

(defn max-norm
  "Max Norm of a vector
  l_p norm when p -> Infinity"
  [v]
  (apply max (map #(Math/abs %) v)))
