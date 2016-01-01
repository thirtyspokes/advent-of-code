(ns advent-of-code.day_one.apartment-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day_one.apartment :refer :all]))

(deftest calculate-floor-test
  (testing "That floors are correctly calculated."
    (is (= 0 (find-floor "(())")))
    (is (= 0 (find-floor "()()")))
    (is (= 3 (find-floor "(((")))
    (is (= 3 (find-floor "(()(()(")))
    (is (= 3 (find-floor "))(((((")))
    (is (= -1 (find-floor "())")))
    (is (= -1 (find-floor "))(")))
    (is (= -3 (find-floor ")))")))
    (is (= -3 (find-floor ")())())")))))

(deftest first-basement-floor-test
  (testing "That the first basement floor is correctly calculated."
    (is (= 1 (find-first-basement-step ")")))
    (is (= 5 (find-first-basement-step "()())")))))
