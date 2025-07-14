(ns main
  (:require
   [research.data.data-processor :refer :all]
   [research.repositories :as milk-repo]
   [research.system :as system]))


(defn -main []
(let [ data (milk-repo/list-all)]
  ;; (println "Loaded" (count data) "milk samples")
  (println "==================================")
  (println "========== Loy Yee Ko ============")
  (println "====== Research on Clojure =======")
  (println "==================================")
  ;; (pprint/print-table  data))
  (system/start-system)
  (println "==================================")
  (println "========== Loy Yee Ko ============")
  (println "====== Research on Clojure =======")
  (println "==================================")
  ))

  (system/start-system)