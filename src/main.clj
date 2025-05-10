(ns main)

(defn -main []
  (println "Hello World!"))

(defrecord Person [name age male?])
(def d (Person. "david" 39 true))
(-> d :age)
(:name d)
(def g (assoc d :name "Geroge"))

d
g
