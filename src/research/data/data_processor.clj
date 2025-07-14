(ns research.data.data-processor
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [research.models.milk :as models]
   [research.utils :refer [parse-year safe-parse-double]]))


(defn parse-milk
  "Parsing into a Milk object from the CSV header column names."
  [[sample type start-date stop-date station provence sr90-activity sr90-error sr90-calcium]]
  (models/->Milk
   sample
   type
   (parse-year start-date)
   (parse-year stop-date)
   station
   provence
   (safe-parse-double sr90-activity)
   (safe-parse-double sr90-error)
   (safe-parse-double sr90-calcium)))

(defn load-data [filename]
  (with-open [reader (io/reader filename)]
    (rest (doall
           (csv/read-csv reader)))))

;; retired.
(defn- read-csv
  "Reading csv from resources directory

  parameters -  filename: String with .csv
  
  returns - rows of csv except header.
   "
  [^String filename]
  (let [raw (slurp (str "resources/" filename))
        ;; thread-first macro, pass "raw" to the first position in the argument.
        lines (-> raw
                  (str/replace #"\r" "")
                  (str/split #"\n"))
        rows (map #(str/split % #",") lines)]
    ;; skip header 
    (rest rows)))