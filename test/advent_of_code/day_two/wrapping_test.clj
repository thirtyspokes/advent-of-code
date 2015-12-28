(ns advent-of-code.day_two.wrapping-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day_two.wrapping :refer :all]))

(deftest calculate-wrapping-paper-test
  (testing "that wrapping paper amount is calculated."
    (is (= 58 (calculate-paper "2x3x4")))
    (is (= 43 (calculate-paper "1x1x10")))))

(deftest calculate-total-ribbon-test
  (testing "that the ribbon amount is calculated."
    (is (= 34 (calculate-ribbon "2x3x4")))
    (is (= 14 (calculate-ribbon "1x1x10")))))
