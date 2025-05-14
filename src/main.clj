(ns main
  (:require
   [clojure.pprint :as pprint]
   [clojure.string :as str]
   [tick.core :as t]))

;; A record representation of CSV header column names
(defrecord
 Milk
 [sample type start-date stop-date station-name province sr90-activity sr90-error sr90-calcium])

(defn safe-parse-double [^String str-double]
  (try (Double/parseDouble str-double) (catch Exception _ str-double)))

(-> (t/parse-date "01-Jan-84" (t/formatter "dd-MMM-yy"))
    (t/date)
    (str))

(defn parse-year
  "handling year with only 2 ending digits"
  [date-string]
  (println date-string)
  (let  [[day month yr] (str/split date-string #"-")
         full-year (if (> (parse-long yr) 50)
                     (str "19" yr)
                     (str "20" yr))
         parsed-date (str day "-" month "-" full-year)]
    (str (t/parse-date parsed-date (t/formatter "dd-MMM-yyyy")))))
(parse-year "30-Sep-84")

(t/parse-date "30-Sept-1986" (t/formatter "dd-MMM-yyyy"))


(defn parse-milk
  "Parsing into a Milk object from the CSV header column names."
  [[sample type start-date stop-date station provence sr90-activity sr90-error sr90-calcium]]
  (Milk.
   sample
   type
   ;;  (parse-year start-date)
   ;;  (parse-year stop-date)
   start-date
   stop-date
   station
   provence
   (safe-parse-double sr90-activity)
   (safe-parse-double sr90-error)
   (safe-parse-double sr90-calcium)))

(defn read-csv
  "Reading csv from resources directory

  parameters -  filename: String with .csv
  
  returns - rows of csv except header.
   "
  [^String filename]
  (rest (str/split
         (str/replace (slurp (str "resources/" filename)) "\r" "") #"\n")))

(defn process-data
  "Processing CSV data from resources directory into a collection of Milk records."
  []
  (let [lines (read-csv "nms_strontium90_milk_ssn_strontium90_lait.csv")
        milk-data (map
                   (fn [line] (let [content (str/split line #",")] (parse-milk content)))
                   lines)]
    milk-data))

(defn -main []
  (let [milk-data (process-data)]
    (println "Loaded" (count milk-data) "milk samples")
    (pprint/print-table  milk-data)))