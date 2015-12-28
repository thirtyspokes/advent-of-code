(ns advent-of-code.day_one.apartment)

(defn step-to-move
  "Given the characters ( or ), which represent moving up or down one
  floor (respectively), returns either 1, -1, or 0 (if the input was not
  one of the valid moves."
  [step]
  (case step
    \( 1
    \) -1
    0))

(defn steps-with-index
  "Translates a string of move characters ('(' or ')') to a list of vectors
  in the format [index move-value], with indexes starting at one.

  Example: '())(' => ([1 1] [2 -1] [3 -1] [4 1]"
  [input]
  (map-indexed #(vector (inc %1) (step-to-move %2)) input))

(defn add-step-with-floor
  "Given an accumulator representing the steps traversed so far, and
  a vector containing an index and a move value, returns the result of
  adding a new step as a vector in the format [index current-floor], where
  the current floor is the previous floor plus this step's move value."
  [acc current-step]
  (cons (vector (first current-step)
                (+ (second current-step)
                   (second (first acc))))
        acc))

(defn steps-with-floor
  "Takes an input string of moves either up or down, and returns a list of vectors
  in the format [index resulting-floor] representing the floor that each step
  takes Santa to."
  [input]
  (reverse (reduce add-step-with-floor [[0 0]] (steps-with-index input))))

(defn steps-until-basement
  "Returns all of the steps until the first basement step is reached."
  [input-with-steps]
  (take-while #(<= -1 (second %1)) input-with-steps))

(defn find-floor
  "Given an input string of moves, returns the final floor that Santa lands on."
  [input]
  (reduce + (map step-to-move input)))

(defn find-first-basement-step
  "Given an input string of moves, returns the first step that results in Santa
  reaching the basement."
  [input]
  (-> input
      steps-with-floor
      steps-until-basement
      last
      first))
