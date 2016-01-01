(ns advent-of-code.day_five.strings)

(defn has-three-vowels?
  "Determines if the input string contains at least three
  occurrences of any vowel."
  [input]
  (<= 3
      (count (filter #(contains? #{\a \e \i \o \u} %) input))))

(defn has-repeated-letter?
  "Returns true if the input string contains any instance
  of the same letter repeated twice."
  [input]
  (let [[start & tail] input]
    (loop [letter start remaining tail]
      (cond (= '() remaining) false
            (= letter (first remaining)) true
            :else
            (recur (first remaining) (rest remaining))))))

(defn has-no-bad-seqs?
  "Returns true if the input string does not contain any of
  the sequences 'ab', 'cd', 'pq', and 'xy'."
  [input]
  (nil? (re-find #"ab|cd|pq|xy" input)))

(defn has-repeated-sequence?
  "Returns true if the input string contains any two-letter sequence
  of letters repeated twice, but not overlapping.

  For example, 'abasdfab' returns true ('ab'), but 'aaaxda' will not
  return true for the 'aa' sequence.  Adjacent but not overlapping
  sequences are OK - 'abcdiiii' will return true, as will 'abcdiaia'."
  [input]
  (= true
     (reduce (fn [seen current]
               (if (and (some #(= % current) seen) (or (not= current (first seen))
                                                       (= current 
                                                          (first seen)
                                                          (second seen))))
                 (reduced true)
                 (cons current seen)))
             []
             (partition 2 1 input))))

(defn has-gapped-repeating-letter?
  "Returns true if the input string has any instance of the same
  letter repeated twice but separated by some other character, such
  as 'axa'."
  [input]
  (seq
   (filter (fn [[a _ b]] (= a b))
           (partition 3 1 input))))

(defn naughty-or-nice?
  "A 'nice' string returns true for `has-three-vowels?`, 
  `has-repeatedletter?`, and `has-no-bad-seqs?`."
  [input]
  (if (and (has-three-vowels? input)
            (has-repeated-letter? input)
            (has-no-bad-seqs? input))
    :nice
    :naughty))

(defn better-naughty-or-nice?
  "A 'nice' string returns true for both `has-gapped-repeating-letter?`
  and `has-repeated-sequence?`."
  [input]
  (if (and (has-gapped-repeating-letter? input)
           (has-repeated-sequence? input))
    :nice
    :naughty))

(defn count-nice-strings
  [input]
  (count (filter (partial = :nice) (map naughty-or-nice? input))))

(defn count-nicer-strings
  [input]
  (count (filter (partial = :nice) (map better-naughty-or-nice? input))))
