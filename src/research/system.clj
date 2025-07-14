(ns research.system
  (:require
   [research.routes :as routes]
   [ring.adapter.jetty :as jetty])
  (:import
   (org.eclipse.jetty.server Server)))

(defn start-server [system]
  
  ;;Using the var reference #' instead of the function value gives you REPL-friendly development:
  (jetty/run-jetty (partial #'routes/root-handler system) ;; this is a func not been called
                   {:port 8001
                    :join? false}))

(defn stop-server [server]
  (Server/.stop server))

(defn start-system []
  (let [system-so-far  {}]
    ;; ::server is a namespace keyword
    {::server (start-server system-so-far)}))

(defn stop-system [system]
  (stop-server (::server system)))
