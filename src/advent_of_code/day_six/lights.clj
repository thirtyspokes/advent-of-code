(ns advent-of-code.day_six.lights)

(defn string-to-coordinates
  [input]
  (map #(Integer/parseInt %) (clojure.string/split input #",")))

(defn parse-command
  [input]
  (let [pieces (clojure.string/split input #" ")]
    (case (first pieces)
      "toggle" {:action :toggle 
                :start (string-to-coordinates (nth pieces 1))
                :end (string-to-coordinates (nth pieces 3))}
      "turn"   {:action (keyword (nth pieces 1))
                :start (string-to-coordinates (nth pieces 2))
                :end (string-to-coordinates (nth pieces 4))})))

(defn change-light
  [lights light command]
  (case (:action command)
    :toggle (if (nil? (@lights light))
              (reset! lights (conj @lights light))
              (reset! lights (disj @lights light)))
    :on (reset! lights (conj @lights light))
    :off (reset! lights (disj @lights light))))

(defn change-light-brightness
  [lights light command]
  (case (:action command)
    :toggle (if (nil? (@lights light))
              (reset! lights (assoc @lights light 2))
              (reset! lights (assoc @lights light (+ 2 (@lights light)))))
    :on (if (nil? (@lights light))
          (reset! lights (assoc @lights light 1))
          (reset! lights (assoc @lights light (inc (@lights light)))))
    :off (if (and (not (nil? (@lights light)))
                  (< 0 (@lights light)))
           (reset! lights (assoc @lights light (dec (@lights light))))
           lights)))

(defn execute-command
  [command lights]
  (let [[x1 y1] (:start command)
        [x2 y2] (:end command)] 
    (for [x (range x1 (inc x2)) y (range y1 (inc y2))]
      (change-light lights [x y] command))))

(defn execute-brightness-command
  [command lights]
  (let [[x1 y1] (:start command)
        [x2 y2] (:end command)]
    (for [x (range x1 (inc x2)) y (range y1 (inc y2))]
      (change-light-brightness lights [x y] command))))

(defn command-lights
  [command lights]
  (last (execute-command (parse-command command) lights)))

(defn command-light-brightness
  [command lights]
  (last (execute-brightness-command (parse-command command) lights)))

(defn execute-all-commands
  [input-text lights]
  (count (last (map #(command-lights % lights) (clojure.string/split-lines input-text)))))

(defn execute-all-brightness-commands
  [input-text lights]
  (last (map #(command-light-brightness % lights) (clojure.string/split-lines input-text))))

(defn get-total-brightness
  [input-text lights]
  (reduce + 0 (map #(@lights %) (keys (execute-all-brightness-commands input-text lights)))))
