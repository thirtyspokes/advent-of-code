(ns advent-of-code.day_five.strings)

(defn has-three-vowels?
  [input]
  (<= 3
      (count (filter #(contains? #{\a \e \i \o \u} %) input))))

(defn has-repeated-letter?
  [input]
  (let [[start & tail] input]
    (loop [letter start remaining tail]
      (cond (= '() remaining) false
            (= letter (first remaining)) true
            :else
            (recur (first remaining) (rest remaining))))))

(defn has-no-bad-seqs?
  [input]
  (nil? (re-find #"ab|cd|pq|xy" input)))

(defn has-repeated-sequence?
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
  [input]
  (seq
   (filter (fn [[a _ b]] (= a b))
           (partition 3 1 input))))

(defn naughty-or-nice?
  [input]
  (if (and (has-three-vowels? input)
            (has-repeated-letter? input)
            (has-no-bad-seqs? input))
    :nice
    :naughty))

(defn better-naughty-or-nice?
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
