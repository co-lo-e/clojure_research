(ns research.milk.routes
  (:require
   [cheshire.core :as ches]
   [research.data.data-reader :refer [milk-data]]
   [research.milk.components :refer [milk-table page-body]]))

(defn list-handler [system _request]
  (try
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (page-body "Clojure Research" "Milk Records" milk-table)}
    (catch Exception e
      (println (.getMessage e))
      {:status 500
       :headers {"Content-Type" "text/html"}
       :body (page-body "Clojure Research" "Milk Records" [:h1 "Internal Server Error."])})))

(defn get-milk-records-handler [system _request]
  (try
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (ches/generate-string milk-data)}
    (catch Exception e
      (println (.getMessage e))
      {:status  500
       :headers {"Content-Type" "application/json"}
       :body (ches/generate-string {:message (.getMessage e)})})))

(defn routes [system]
  [["/milk-records" {:get {:handler (partial #'list-handler system)}}]
   ["/api/milk-records" {:get {:handler (partial #'get-milk-records-handler system)}}]])