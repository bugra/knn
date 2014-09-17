![Lisp Cycles](http://imgs.xkcd.com/comics/lisp_cycles.png "These are your father's parentheses")

# knn

knn is a k nearest neigbor classifier module written in pure Clojure. It supports a variety of distance functions out of the box, listed below. It has full test coverage.

## Usage

FIXME

## License

Copyright © 2014 Bugra Akyildiz

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## Future
I am planning to support [PigPen](https://github.com/Netflix/PigPen) in future.

### Supported Distance Functions and Formulas
Adapted from [Distances.jl](https://github.com/JuliaStats/Distances.jl)

| type name            |  convenient syntax   | math definition     |
| -------------------- | -------------------- | --------------------|
|  Euclidean           |  euclidean(x, y)     | sqrt(sum((x - y) .^ 2)) |
|  SqEuclidean         |  sqeuclidean(x, y)   | sum((x - y).^2) |
|  Cityblock           |  cityblock(x, y)     | sum(abs(x - y)) |
|  Chebyshev           |  chebyshev(x, y)     | max(abs(x - y)) |
|  Minkowski           |  minkowski(x, y, p)  | sum(abs(x - y).^p) ^ (1/p) |
|  Hamming             |  hamming(x, y)       | sum(x .!= y) |
|  CosineDist          |  cosine_dist(x, y)   | 1 - dot(x, y) / (norm(x) * norm(y)) |
|  CorrDist            |  corr_dist(x, y)     | cosine_dist(x - mean(x), y - mean(y)) |
|  ChiSqDist           |  chisq_dist(x, y)    | sum((x - y).^2 / (x + y)) |
|  KLDivergence        |  kl_divergence(x, y) | sum(p .* log(p ./ q)) |
|  JSDivergence        |  js_divergence(x, y) | KL(x, m) / 2 + KL(y, m) / 2 with m = (x + y) / 2 |
|  SpanNormDist        |  spannorm_dist(x, y) | max(x - y) - min(x - y ) |
|  BhattacharyyaDist   |  bhattacharyya(x, y) | -log(sum(sqrt(x .* y) / sqrt(sum(x) * sum(y))) |
|  HellingerDist       |  hellinger(x, y)     | sqrt(1 - sum(sqrt(x .* y) / sqrt(sum(x) * sum(y)))) |
|  WeightedMinkowski   |  minkowski(x, y, w, p)   | sum(abs(x - y).^p .* w) ^ (1/p)  |
