(ns main
  (:require
   [clojure.pprint :as pprint]
   [data-processor :refer :all]
   [repositories :as milk-repo]
   ))


(defn -main []
(let [ data (milk-repo/list-all)]
  (println "Loaded" (count data) "milk samples")
  (pprint/print-table  data)))