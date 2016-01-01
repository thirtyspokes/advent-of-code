(ns advent-of-code.day_five.strings-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day_five.strings :refer :all]))

(deftest naughty-or-nice-string-test
  (testing "determines whether a string is naughty or nice"
    (is (= :nice (naughty-or-nice? "ugknbfddgicrmopn")))
    (is (= :nice (naughty-or-nice? "aaa")))
    (is (= :naughty (naughty-or-nice? "jchzalrnumimnmhp")))
    (is (= :naughty (naughty-or-nice? "haegwjzuvuyypxyu")))
    (is (= :naughty (naughty-or-nice? "dvszwmarrgswjxmb")))))

(deftest better-naughty-or-nice-string-test
  (testing "superior determinant of a naughty or nice string"
    (is (= :nice (better-naughty-or-nice? "qjhvhtzxzqqjkmpb")))
    (is (= :nice (better-naughty-or-nice? "xxyxx")))
    (is (= :naughty (better-naughty-or-nice? "uurcxstgmygtbstg")))
    (is (= :naughty (better-naughty-or-nice? "ieodomkazucvgmuy")))))
