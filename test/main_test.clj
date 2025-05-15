(ns main-test
(:use clojure.test)
  (:require
   [main :refer :all]))

(deftest test-parse-milk
  (testing "Parsing a valid milk record"
   	
		(let [record ["sample1" "type1" "01-Jan-84" "31-Dec-84" "station1" "province1" "8.12E-02" "1.89E-02" "7.89E-02"]
      		milk (parse-milk record)]
				(is (= (:sample milk) "sample1"))
				(is (= (:type milk) "type1"))
				(is (= (:start-date milk) "1984-01-01"))
				(is (= (:stop-date milk) "1984-12-31"))
				(is (= (:station-name milk) "station1"))
				(is (= (:province milk) "province1"))
				(is (= (:sr90-activity milk) 0.0812))
				(is (= (:sr90-error milk) 0.0189))
				(is (= (:sr90-calcium milk) 0.0789))
				)
  		))

(deftest test-safe-parse-double
  (testing "Parsing valid and invalid doubles"
    (is (= (safe-parse-double "10.5") 10.5))
    (is (= (safe-parse-double "invalid") "invalid"))))