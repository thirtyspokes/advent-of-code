(ns advent-of-code.day_four.hashes
  (:require [digest :as hash]))

(defn find-hash
  "Returns the lowest positive integer where the md5 digest of `key` and
  that number produces a hash with `number-of-zeroes` leading zeroes."
  [key number-of-zeroes]
  (loop [num 1]
    (if (every? (partial = \0) (take number-of-zeroes (digest/md5 (str key num))))
      num
      (recur (inc num)))))
