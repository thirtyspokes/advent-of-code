(ns advent-of-code.day_two.wrapping
  (:require [clojure.string :as str]))

(defn get-slack
  "The slack required to wrap a present is the area of
  its smallest side."
  [dimensions]
  (let [sorted (sort dimensions)
        [a b c] sorted]
    (* a b)))

(defn parse-string-input
  "Given a string presentation of the dimensions
  of a present in the format 'LxWxH', returns just the numeric
  values as integers."
  [input]
  (map #(Integer/parseInt %1) (str/split input #"x")))

(defn calculate-paper
  "The square footage of paper required to wrap a present is
  the surface area of the entire box, plus some extra slack which
  is equal to the area of the box's smallest side."
  [input]
  (let [dimensions (parse-string-input input)
        [length width height] dimensions]
    (+ (* 2 length width)
       (* 2 width height)
       (* 2 height length)
       (get-slack dimensions))))

(defn calculate-ribbon
  "The length of ribbon in feet required for each present is equal
  to the smallest perimeter of any one face plus an extra amount
  for the bow equal to the cubic volume of the package."
  [input]
  (let [dimensions (parse-string-input input)
        [a b c] (sort dimensions)]
    (+ (* 2 a)
       (* 2 b)
       (* a b c))))

(defn calculate-total-paper
  "Given a list of present dimensions in the format 'LxWxH', returns
  the total square footage of paper required to wrap all of them."
  [lines]
  (reduce + (map calculate-paper lines)))

(defn calculate-total-ribbon
  "Given a list of present dimensions in the format 'LxWxH', returns
  the total length of ribbon in feet required to decorate all of them."
  [lines]
  (reduce + (map calculate-ribbon lines)))
