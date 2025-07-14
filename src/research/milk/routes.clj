(ns research.milk.routes
  (:require
   [cheshire.core :as ches]
   [hiccup2.core :as h]
   [research.data.data-reader :refer [milk-data]]))
(defn list-handler [system _request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str
          (h/html
           [:html
            [:head
             [:title "Milk Records"]
             [:link {:rel "stylesheet"
                     :href "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"}]]
            [:body
             [:div {:class "container mt-4"}
              [:h1 "Milk Records"]
              (milk-table)]]]))})

(defn get-milk-records-handler [system _request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (ches/generate-string milk-data)})

(defn routes [system]
  [["/milk-records" {:get {:handler (partial #'list-handler system)}}]
   ["/api/milk-records" {:get {:handler (partial #'get-milk-records-handler system)}}]])