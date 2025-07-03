(ns data-reader
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]
   [clojure.tools.logging :as log]))

(defn safe-parse-double [s]
  (try
    (Double/parseDouble s)
    (catch Exception e
      (log/warn (str "Could not parse '" s "' as double: " (.getMessage e)))
      nil)))

(def milk-data
  (with-open [rdr (io/reader "resources/nms_strontium90_milk_ssn_strontium90_lait.csv")]
    (let [data (csv/read-csv rdr)
          keywords [:sample :type :start-date :end-date :station :province :sr90-activity :sr90-error :sr90-calcium]
          rows (into [] (rest data))] ;; use intermediate collection to avoid lazy
      (mapv (fn [row]
              (let [m (zipmap keywords row)] ;; zip keyword with cell
                (-> m
                    (assoc :id (java.util.UUID/randomUUID))
                    (assoc :sr90-activity (safe-parse-double (:sr90-activity m)))
                    (assoc :sr90-error (safe-parse-double (:sr90-error m)))
                    (assoc :sr90-calcium (safe-parse-double (:sr90-calcium m)))))) rows))))

milk-data
(def provinces
  (->> milk-data
       (map :province)
       distinct
       sort))

(def stations
  (->> milk-data
       (map :station)
       distinct
       sort))
#_provinces
#_stations

#_(frequencies (map #(:station %) milk-data))