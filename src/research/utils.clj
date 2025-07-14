(ns research.utils
  (:require
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   [tick.core :as t]))

(defn uuid-parser [id]
  (try
    (let [uuid-obj
          (cond
            (uuid? id) id
            (string? id) (java.util.UUID/fromString id)
            (nil? id) (throw (ex-info "cannot be nil" {:provided id}))
            :else (throw (ex-info "must be string or uuid" {:provided id :type (type id)})))]
      uuid-obj)
    (catch IllegalArgumentException e (log/error (str "Invalid: " (.getMessage e))))))

;; (defn safe-parse-double [s]
;;   (try
;;     (Double/parseDouble s)
;;     (catch Exception e
;;       (log/warn (str "Could not parse '" s "' as double: " (.getMessage e)))
;;       nil)))


(defn text-red [text]
  (str "\u001b[31m" text "\u001b[0m"))

(defn text-blue [text]
  (str "\u001b[34m" text "\u001b[0m"))

(defn ctext [text colour]
  (cond colour
        (= colour :blue)))

(defn safe-parse-double
  "String to double parser, when failed to parse return empty string
 argument: String - e.g.: '0.1' '2.3E-2', not 'abc'
 return: 0.1, 0.023, or ''
 "
  [^String str-double]
  (cond
    (nil? str-double) nil
    (empty? str-double) nil
    :else (try (Double/parseDouble str-double)
               (catch Exception _
                 (log/warn  "Failed to parse string to double,\n[WHY] check your input must be" (text-red "Double in String") "but your input" (text-blue str-double) "is not Double in String")
                 nil))))


#_(safe-parse-double "")
#_(safe-parse-double "abc")
#_(safe-parse-double "1.2")
#_(safe-parse-double "23.4E-9")


(defn- count-hyphens
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
    ;; (throw (ex-info "Date string must have exactly two hyphens (dd-MMM-yy)" {:date-string date-string}))
    (log/error "Date string must have exactly two hyphens (dd-MMM-yy)")
    (let  [[day month yr] (str/split date-string #"-")
           full-year (if (> (parse-long yr) 50)
                       (str "19" yr)
                       (str "20" yr))
           parsed-date (str day "-" month "-" full-year)]
      (str (t/parse-date parsed-date (t/formatter "dd-MMM-yyyy" (java.util.Locale. "en_US")))))))
#_(parse-year "01-Jan-84")
#_(parse-year "01-Jan84")