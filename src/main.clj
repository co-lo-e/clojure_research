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

(defn count-hyphens
  "Accepting date in string with dd-MMM-yy format,
 and return the count of the number of hyphens.

parameters - date-string : String e.g.: '01-Jan-84' 

returns - count: int

 example:
   ```clj
   (count-hyphen \"01-Jan-84\") => 2
   (count-hyphen \"01Jan84\") => 0
   ```
 "
  [date-string]
  (count (apply str (re-seq #"-" date-string))))
#_(count-hyphens "01-Jan-84")
#_(count-hyphens "01-Jan84")
#_(count-hyphens "01Jan84")
#_(count-hyphens "01Jan84---")

(defn parse-year
  "handling year with only 2 ending digits 

parameter: date-string : String e.g. '01-Jan-84'
throws:  
return: ISO 8601 e.g.: '1984-01-01'
   
 example:
   ```clj
   (parse-year \"01-Jan-84\") => \"1984-01-01\"
   (parse-year \"01-Jan84\") => \"01-Jan84\"
   ```
   "
  [date-string]
  ;; check if the hyphen counts is 2,
  ;; if not 2 return input
  ;; else process the date
  (if (not (= 2 (count-hyphens date-string)))
    (throw (ex-info "Date string must have exactly two hyphens (dd-MMM-yy)" {:date-string date-string}))
    (let  [[day month yr] (str/split date-string #"-")
           full-year (if (> (parse-long yr) 50)
                       (str "19" yr)
                       (str "20" yr))
           parsed-date (str day "-" month "-" full-year)]
      (str (t/parse-date parsed-date (t/formatter "dd-MMM-yyyy" (java.util.Locale. "en_US")))))))
#_(parse-year "01-Jan-84")
#_(parse-year "01-Jan84")

(defn parse-milk
  "Parsing into a Milk object from the CSV header column names."
  [[sample type start-date stop-date station provence sr90-activity sr90-error sr90-calcium]]
  (Milk.
   sample
   type
   (parse-year start-date)
   (parse-year stop-date)
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
  (let [raw (slurp (str "resources/" filename))
        ;; thread-first macro, pass "raw" to the first position in the argument.
        lines (-> raw
                  (str/replace "/r" "")
                  (str/split #"\n"))
        rows (map #(str/split % ",") lines)]
    ;; skip header 
    (rest rows)))

(defn process-data
  "Processing CSV data from resources directory into a collection of Milk records."
  []
  (let [rows (read-csv "nms_strontium90_milk_ssn_strontium90_lait.csv")]
    (map parse-milk rows)))

(defn -main []
  (let [milk-data (process-data)]
    (println "Loaded" (count milk-data) "milk samples")
    (pprint/print-table  milk-data)))