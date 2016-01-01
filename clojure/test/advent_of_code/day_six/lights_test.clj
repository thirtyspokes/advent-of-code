(ns advent-of-code.day_six.lights-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day_six.lights :refer :all]))

(deftest light-manipulation-test
  (testing "that lights are correctly turned off or on"
    (is (= 1000000 (count (command-lights "turn on 0,0 through 999,999" (atom #{})))))
    (is (= 1000 (count (command-lights "toggle 0,0 through 999,0" (atom #{})))))
    (is (= 0 (count (command-lights "turn off 499,499 through 500,500" (atom #{})))))))
