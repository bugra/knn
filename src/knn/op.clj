(ns knn.op)

(defn add
  "Addition of two vectors"
  [first-vector second-vector]
  (map + first-vector second-vector))

(defn subtract
  "Subtraction of two vectors"
  [first-vector second-vector]
  (map - first-vector second-vector))

(defn mult
  "Multiplication of two vectors element-wise"
  [first-vector second-vector]
  (map * first-vector second-vector))

(defn div
  "Division of two vectors element-wise"
  [first-vector second-vector]
  (map / first-vector second-vector))

(defn dot
  "Dot product of two vectors"
  [first-vector second-vector]
  (reduce + (map * first-vector second-vector)))

(defn scalar-add
  "Add a scalar to a vector"
  [v scalar]
  (map #(+ scalar %) v))

(defn scalar-subtract
  "Subtract a scalar from a vector"
  [v scalar]
  (map #(- % scalar) v))

(defn scalar-mult
  "Multiply a scalar with a vector by element-wise"
  [v scalar]
  (map #(* scalar %) v))

(defn scalar-div
  "Divide a scalar with a vector by element-wise"
  [v scalar]
  (map #(/ % scalar) v))

(defn clamp
  "Clamp the vector between minimum and maximum values"
  [cmin cmax v]
  (map #(max cmin (min cmax %)) v))

(defn mean
  "Mean of a vector"
  [v]
  (/ (reduce + v) (count v)))

(defn moving-average
  "Moving average of a vector for a given window"
  [v window] (map mean (partition window 1 v)))

(defn squared
  "Returns element-wise squared version of the vector"
  [v]
  (map #(* % %) v))

(defn abs
  "Element-wise absolute operation to a vector"
  [v]
  (map #(Math/abs %) v))

(defn pow
  "Element-wise power operation to a vector"
  [v scalar]
  (map #(Math/pow % scalar) v))
