(ns advent-of-code.day_three.presents)

(defn translate-move
  "Translates a character representing a direction into a delta for
  an x,y coordinate system."
  [direction]
  (case direction
    \^ [0 1]
    \v [0 -1]
    \> [1 0]
    \< [-1 0]
    [0 0]))

(defn move
  "Given a current location and a direction for movement, returns the coordinates
  that result from moving to the new location."
  [current new]
  (vec (map + current new)))

(defn traverse-locations
  "Provided a list of locations already visited and a direction
  for the current step, moves Santa in the indicated direction and adds
  the new location to the list of visited squares on the grid."
  [visited current-direction]
  (let [direction (translate-move current-direction)]
    (cons (move direction (first visited)) visited)))

(defn get-movement-path
  "Given a list of moves representing directions on an infinite
  2-D grid, walks along the grid one move at a time and returns each
  square on the grid that was visited (in reverse order)."
  [moves]
  (reduce traverse-locations '([0 0]) moves))

(defn assign-move
  "Given a list of the current moves (for Santa or Robosanta),
  and a new step that consists of a move and that move's index in a larger
  list, assigns the move to either Santa or Robosanta."
  [moves step]
  (let [[index move] step]
    (if (odd? index)
      [(cons move (first moves)) (second moves)]
      [(first moves) (cons move (second moves))])))

(defn moves-with-index
  "Returns the input list of moves with each move's index."
  [moves]
  (map-indexed (fn [index item] [index item]) moves))

(defn get-move-assignments
  "Given a move list, assigns the move to one of two collections
  in alternate order."
  [input]
  (reduce assign-move [[] []] (moves-with-index input)))

(defn number-of-deliveries
  "Returns the number of houses visited at least once
  by a Santa moving along an infinite 2-D grid."
  [moves]
  (count (set (get-movement-path moves))))

(defn number-of-team-deliveries
  "Returns the number of houses visited at least once, where
  Santa and an identical Robo Santa take turns moving from the
  list of moves provided."
  [moves]
  (let [[santa-moves robo-moves] (get-move-assignments moves)
        santa (get-movement-path (reverse santa-moves))
        robosanta (get-movement-path (reverse robo-moves))]
    (count (set (into santa robosanta)))))
