(ns research.routes
  (:require
   [reitit.ring :as ring]
   [research.milk.routes :as milk-routes]))

;; (defn hello-handler [system _request]
;;   {:status 200
;;    :headers {"Content-Type" "text/html"}
;;    :body "<h1> Hello World</h1>"})

;; (defn bye-handler [system _request]
;;   {:status 200
;;    :headers {"Content-Type" "text/html"}
;;    :body "<h1> Bye World</h1>"})



(defn routes
  "The grand center routes board"
  [system]
  [""
  ;;  ["/" {:get {:handler (partial #'hello-handler system)}}]
  ;;  ["/bye" {:get {:handler (partial #'bye-handler system)}}]
   (milk-routes/routes system)])


(defn root-handler
  "returns any handler from the routes value such as hello-handler or bye-handler"
  [system request]
  (let [handler (ring/ring-handler
                 (ring/router
                  (routes system)))]
    (handler request)))