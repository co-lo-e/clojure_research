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
				(is (= (:sample milk) "01-Jan-84"))
				(is (= (:sample milk) "31-Dec-84"))
				(is (= (:sample milk) "station1"))
				(is (= (:sample milk) "province1"))
				(is (= (:sample milk) 0.0812))
				(is (= (:sample milk) 0.0189))
				(is (= (:sample milk) 0.0789))
				)
  		))

(deftest test-safe-parse-double
  (testing "Parsing valid and invalid doubles"
    (is (= (safe-parse-double "10.5") 10.5))
    (is (= (safe-parse-double "invalid") "invalid"))))