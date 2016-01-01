(ns advent-of-code.day_three.presents-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day_three.presents :refer :all]))

(deftest calculate-deliveries-test
  (testing "that the number of houses visited is calculated"
    (is (= 2 (number-of-deliveries ">")))
    (is (= 4 (number-of-deliveries "^>v<")))
    (is (= 2 (number-of-deliveries "^v^v^v^v^v")))))

(deftest calculate-team-deliveries-test
  (testing "that number of houses visited by the team is calculated"
    (is (= 3 (number-of-team-deliveries "^v")))
    (is (= 3 (number-of-team-deliveries "^>v<")))
    (is (= 11 (number-of-team-deliveries "^v^v^v^v^v")))))
