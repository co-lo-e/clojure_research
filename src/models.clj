(ns models)


;; A record representation of CSV header column names
(defrecord
 Milk
 [sample type start-date stop-date station province sr90-activity sr90-error sr90-calcium])