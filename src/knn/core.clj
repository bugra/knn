(ns knn.core)

(use '[knn.distance :as distance])
(use 'clojure.java.io)

; Observation is the abstraction layer that captures observation with label
(defstruct observation :label :observation)

(defn- counter
  "Counter of the value in the vector"
  [v value]
  (count (filter (partial == value) v)))

(defn- majority-label
  "Labeling schema among observations"
  [observations]
  (last (sort-by (partial counter (map :label observations)) (set (map :label observations)))))

(defn- nearest-neighbors
  "Return the closest k nearest neighbors based on distance function"
  [observation data distance-function k]
  (take k (sort-by #(distance-function (:observation observation)
					(:observation %)) data)))

; TODO
; Factorization of matrix
(defn- pairwise-distance-matrix
  "Pairwise distance matrix for observations"
  [vectors distance-function]
  (vec (map #(partial distance-function %) vectors)))

; TODO
; Make the labeling schema optional(all)
(defn predict
  "Predict the example based on training"
  [training test-data distance-function k]
  (vec (map #(majority-label (nearest-neighbors % training distance-function k)) test-data)))

(defn read-lines
  "Return the file contents in the form of a vector
  where every line is an element"
  [file-path]
  (with-open [x (reader file-path)]
    (vec (line-seq x))))

(defn parse-line
  "Parse line into two separate parts"
  [line]
  (#(struct observation (first %) (rest %)) (map #(Float/parseFloat %) (.split line " "))))

(defn -main
  "Main Function"
  [& args]
  (def train-file-path "data/train.txt")
  (def test-file-path "data/test.txt")
  (def k 5)
  (def training (vec (map parse-line (read-lines train-file-path))))
  (def test-data (vec (map parse-line (read-lines test-file-path))))
  (println training)
  (println test-data)
  (println (predict training test-data distance/euclidean-distance 3)))
