(ns main
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn -main []
  (println "Hello World!"))

(println (seq (.list (java.io.File. "resources"))))
(print (slurp  (io/resource "nms_strontium90_milk_ssn_strontium90_lait.csv")))
(def csv (str/replace (slurp  "resources/nms_strontium90_milk_ssn_strontium90_lait.csv") "\r" ""))
(print csv)

(defrecord Milk [sample type start-date stop-date station-name province sr90-activity sr90-error sr90-calcium])

(defn process-data []
  (let [lines (rest (str/split csv #"\n"))
        milk-data (map
                   (fn [line]
                     (let [[sample type start-date stop-date station provence sr90-activity sr90-error sr90-calcium] (str/split line #",")]
                       (Milk. sample type start-date stop-date station provence sr90-activity sr90-error sr90-calcium)))
                   lines)]
    milk-data))

(process-data)
((complement nil?))
