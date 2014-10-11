![Lisp Cycles](http://imgs.xkcd.com/comics/lisp_cycles.png "These are your father's parentheses")

# knn
[![Clojars Project](http://clojars.org/knn/latest-version.svg)](http://clojars.org/knn)
[![TravisCI](http://img.shields.io/travis/bugra/knn.svg?style=flat)](https://travis-ci.org/bugra/knn)

knn is a k nearest neighbor classifier library written in Clojure. 
It supports a variety of distance functions out of the box, listed below. 
It has full test coverage.

## Usage

```clojure
(use ‘[knn.core :refer :all])
(use ‘[knn.distance :refer :all])
(def neighbors 3)
; After loading training data and test data into vectors(the observation vectors need to be same size)
; Predictions are the vector that has the class predictions for each observation
(def predictions (predict training-data test-data manhattan-distance neighbors)
```

## Documentation
[Documentation Page](http://bugra.github.io/knn/)

### Generating Documentation
After cloning the repo, in order to generate the documentation:  
```bash
; For single page documentation(uberdoc.html)
lein marg
; For multi page documentation(one page per module)
lein marg --multi
```

## License

Copyright © 2014 Bugra Akyildiz

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## Future
I am planning to support [PigPen](https://github.com/Netflix/PigPen) in future.

### Supported Distance Functions and Formulas
Adapted from [Distances.jl](https://github.com/JuliaStats/Distances.jl)

| Distance name        |  Syntax                                  | math definition     |
| -------------------- | ---------------------------------------- | --------------------|
|  Euclidean (L_2)     |  `(euclidean-distance x y)`              | sqrt(sum((x - y) .^ 2)) |
|  Squared Euclidean   |  `(squared-euclidean-distance x y)`      | sum((x - y).^2) |
|  Manhattan (L_1)     |  `(manhattan-distance x y)`              | sum(abs(x - y)) |
|  Chebyshev           |  `(chebyshev-distance x y)`              | max(abs(x - y)) |
|  Minkowski (L_P)     |  `(minkowski-distance x y p)`            | sum(abs(x - y).^p) ^ (1/p) |
|  Hamming             |  `(hamming-distance x y)`                | sum(x .!= y) |
|  Cosine              |  `(cosine-distance x y)`                 | 1 - dot(x, y) / (norm(x) * norm(y)) |
|  Correlation         |  `(correlation-distance x y)`            | cosine_dist(x - mean(x), y - mean(y)) |
|  Chi Squared         |  `(chi-squared-distance x y)`            | sum((x - y).^2 / (x + y)) |
|  KLDivergence        |  `(kl-divergence x y)`                   | sum(p .* log(p ./ q)) |
|  JSDivergence        |  `(js-divergence x y)`                   | KL(x, m) / 2 + KL(y, m) / 2 with m = (x + y) / 2 |
|  SpanNormDist        |  `(span-norm-distance x y)`              | max(x - y) - min(x - y ) |
|  BhattacharyyaDist   |  `(bhattacharyya-distance x y)`          | -log(sum(sqrt(x .* y) / sqrt(sum(x) * sum(y))) |
|  HellingerDist       |  `(hellinger-distance x y)`              | sqrt(1 - sum(sqrt(x .* y) / sqrt(sum(x) * sum(y)))) |
|  WeightedMinkowski   |  `(weighted-minkowski-distance  x y w p)`| sum(abs(x - y).^p .* w) ^ (1/p)  |
