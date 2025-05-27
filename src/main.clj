(ns main
  (:require
   [clojure.pprint :as pprint]
   [data-processor :refer :all]
   [repositories :as milk-repo]))


(defn -main []
(let [ data (milk-repo/list-all)]
  (println "Loaded" (count data) "milk samples")
  (println "==================================")
  (println "========== Loy Yee Ko ============")
  (println "====== Research on Clojure =======")
  (println "==================================")
  (pprint/print-table  data))
  (println "==================================")
  (println "========== Loy Yee Ko ============")
  (println "====== Research on Clojure =======")
  (println "==================================")
  )