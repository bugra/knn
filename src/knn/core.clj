(ns knn.core)

(use '[knn.distance :as distance])
(use '[clojure.java.io :as io])
(use '[clojure.string :as string])

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

(defn- pairwise-distance-matrix
  "Pairwise distance matrix for observations"
  [vectors distance-function]
  (vec (map #(partial distance-function %) vectors)))

(defn weighted-labels
  "Aggregated distance to each label based on a distance function"
  [observations distance-function test-instance & {:keys [score-modifier]
                                                   :or {score-modifier identity}}]
  (apply merge-with +
         (map #(hash-map (:label %)
                         (score-modifier (distance-function (:observation test-instance)
                                                            (:observation %)))) observations)))

(defn score-labels
  "Scores for each label based on the aggregation of distances.
  A score modifier function can be selected to be applied to the distance for each instance.
  For instance to convert the distance to a similarity "
  [training test-data distance-function k & {:keys [score-modifier]
                                             :or {score-modifier identity}}]
  (map #(weighted-labels (nearest-neighbors % training distance-function k)
                         distance-function % :score-modifier score-modifier) test-data))

(defn predict
  "Predict the example based on training"
  [training test-data distance-function k]
  (vec (map #(majority-label (nearest-neighbors % training distance-function k)) test-data)))

(defn read-lines
  "Return the file contents in the form of a vector
  where every line is an element"
  [file-path]
  (with-open [x (io/reader file-path)]
    (vec (line-seq x))))

(defn parse-line
  "Parse line into two separate parts"
  [line]
  (#(struct observation (first %) (rest %)) (map #(Float/parseFloat %) (.split line " "))))

(defn parse-vector
  "Parse vector into label and observation"
  [v]
  (#(struct observation (first %) (rest %)) v))

(defn read-csv
  "Read csv file"
  [file-path delimiter]
  (with-open [rd (io/reader (io/file file-path))]
  (->> (line-seq rd)
       (map #(.split ^String % delimiter))
       (mapv vec))))

(defn- convert-iris-labels
  "Convert Iris Labels into integer
  equivalents"
  [label]
  (cond
    (= label "Iris-setosa") 0.0
    (= label "Iris-versicolor") 1.0
    (= label "Iris-virginica") 2.0
   ))

(defn- get-iris-dataset
  "Convert Iris Dataset in the form of
  label observations"
  [iris-file-path]
  (let [iris-dataset (read-csv iris-file-path ",")
        iris-labels (map  convert-iris-labels (map last iris-dataset))
        iris-observations (map #(into [] (map bigdec (butlast %))) iris-dataset)]
    (map parse-vector (map #(into [] %) (map cons iris-labels iris-observations)))))

(defn -main
  "Main Function"
  [& args]
  (def train-file-path "data/train.txt")
  (def test-file-path "data/test.txt")
  ; Number of nearest neighbors
  (def k 5)
  (def training (vec (map parse-line (read-lines train-file-path))))
  (def test-data (vec (map parse-line (read-lines test-file-path))))
  ; Basic dataset predictions
  (println (predict training test-data distance/euclidean-distance k))
  ; Prediction on Iris dataset
  (def iris-file-path "data/iris.csv")
  (def iris-data (get-iris-dataset iris-file-path))
  (def iris-predictions (predict iris-data iris-data distance/euclidean-distance k))
  )
