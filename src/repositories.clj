(ns repositories
  (:require
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   [data-processor :refer :all]))

(defprotocol repositories
  (list-all [])
  (find-by [query])
  (create-data [item])
  (update-data [query item])
  (delete-data [id]))

(defn load-milk-data []
  (try (map parse-milk (load-data  "resources/nms_strontium90_milk_ssn_strontium90_lait.csv"))
       (catch Exception _ (log/warn "Failed to find the file, is the filename correct?"))))

(def milk-data
  (load-milk-data))

milk-data

(defn list-all
  "List all the milk data"
  []
  milk-data)


(defn ele-include?
  "
  [k-val q-val]

  check if the keyword-val contains case insensitive query-val

  Example:
  ```
 (let [k-val-str (get amap akeyword)]
  (include k-val-str \"ab\"))
  ```
   "
  [^String k-val ^String q-value]
  (if-not (nil? k-val)
    (str/includes?
     k-val
     (str/upper-case q-value))
    nil))


(defn ele-between?
  "[k-val min max]

 Take a value in Double from the keyword-value,
 return a element between min and max Double value.
   
if the k-val is nil or non Double it will be nil or false respectively.
   
 Example:
 ```
 (let [k-val-double (get amap akeyword)]
 (ele-between? k-val-double 0 100))
 ```
 "
  [^Double k-val ^Double min ^Double max]
  (when (double? max)
    (if-not (nil? k-val)
      (and (>= k-val min) (<= k-val max))
      false)))

(ele-between? 0.1 0 nil)


(defn filter-between
  "[vmap q-keyword min max]

   q-keyword: one of the keywords in double
   e.g. :sr90-activity, :sr90-error, :sr90-calcium
 
   Filter from a vector of maps, with the query keyword
   to find an element between the min and max 
   if the value is not nil,
   return a sequence.
Example:
   ```
   (between milk-data :sr90-activity 0.06 0.1)
   ```
 "
  [vmap q-keyword min max]
  (let [allowed #{:sr90-activity :sr90-error :sr90-calcium}]
    (if  (contains? allowed q-keyword)
      (filter
       (fn [map] (let [value (get map q-keyword)]
                   (if-not (nil? value) (and (>= value min) (<= value max)) nil)))
       vmap)
      (do (log/warn "Keyword" q-keyword "is not valid please use" allowed) '()))))


(filter-between milk-data :sr90-activity 0.006 0.1)

(defn find-milk
  "[q-keyword q-value]
 
 q-keyword: the query keyword from the Milk defrecord e.g., :station :start-date, :province
 q-value: 
 "
  [q-keyword q-value]
  (filter
   (fn [milk]
     (let [k-val (get milk q-keyword)]
       (if (string? k-val)
         (ele-include? k-val q-value)
         (ele-between? k-val 0 q-value))))
   milk-data))

#_(find-milk :station "CALGARY")
#_(find-milk :sr90-activity  "a")