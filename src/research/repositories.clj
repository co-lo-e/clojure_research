(ns research.repositories
  (:require
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   [research.data.data-processor :refer :all]
   [research.data.data-reader :as d]
   [research.utils :refer [uuid-parser]]))

(defprotocol repositories
  (list-all [this])
  (find-by [this query])
  (create-data [this item])
  (update-data [this query item])
  (delete-data [this id]))


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
                   (if-not (nil? value)
                     (and (>= value min) (<= value max))
                     nil)))
       vmap)
      (do (log/warn "Keyword" q-keyword "is not valid please use" allowed) '()))))


#_(filter-between d/milk-data :sr90-activity 0.006 0.1)

;; ======================================================================= ;;
;;                          Repositories
;; ======================================================================= ;;

(defn load-milk-data []
  (try (map parse-milk (load-data  "resources/nms_strontium90_milk_ssn_strontium90_lait.csv"))
       (catch Exception _ (log/warn "Failed to find the file, is the filename correct?"))))

(def milk-data*
  (atom  d/milk-data))

(defn list-all
  "List all the milk data"
  []
  @milk-data*)
#_(list-all)

(defn find-milk
  "query-map: accept a map with defrecord Milk keywords with query value
 
 case insensitive for string query value,
 
 double values has be to exact

 return: zero or more sequence of defrecord Milk
 Example
 ```clj
 (find-milk {:station \"CALGARY\" :type \"RAW\"})
 ```
 "
  [query-map]
  (filter
   (fn [milk]
     (every?
      (fn [[k v]]
        (let [target-value (get milk k)]
          (cond
            (string? target-value) (ele-include? target-value v)
            (number? target-value) (= target-value v)
            :else (= target-value v))))
      query-map))
   @milk-data*))
#_(find-milk {:province "ON" :type "WHOLE" :start-date "1992" :stop-date "1992"})



(defn find-milk-by-id
  "Find by uuid, when passing the value to the argument use 
 #uuid before the value.
 
 example:
 ```clj
(find-milk-by-d #uuid \"f3j1941290-i1120-3i1\") 
 ```"
  [id]
  (let [uuid-obj (uuid-parser id)]
     (first (filter
                 (fn [milk] (= (:id milk) uuid-obj)) @milk-data*))))

#_(find-milk-by-id #uuid "e4e330e5-ce1c-4e7a-873b-e5919a764bc8")
#_(find-milk-by-id "e4e330e5-ce1c-4e7a-873b-e5919a764bc8")


(defn insert-milk
  "Insert a new milk record"
  [new-milk]
  (swap! milk-data* conj new-milk))

(defn update-milk
  "Updating a milk record by id"
  [id new-milk]
  (let[ uuid (uuid-parser id )]
  (swap! milk-data*
         (fn [milk]
           (map #(if (= (:id %) uuid)
                   new-milk
                   %)
                milk)))))

(defn delete-milk
  "Removing a milk record by id"
  [id]
  (let [uuid (uuid-parser id )]
  (swap! milk-data*
         (fn [milk]
           (remove #(= (:id %) uuid) milk)))))
