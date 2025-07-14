(ns research.milk.components
  (:require
   [research.data.data-reader :refer [milk-data]]))

(defn milk-table []
  [:table {:class "table table-striped"}
   [:thead
    [:tr
     [:th "Sample"]
     [:th "Type"]
     [:th "Station"]
     [:th "Province"]
     [:th "Start Date"]
     [:th "End Date"]
     [:th "sr90 Activity"]
     [:th "sr90 Error"]
     [:th "sr90 Calcium Activity"]]]
   [:tbody
    (for [record milk-data]
      [:tr
       [:td (:sample record)]
       [:td (:type record)]
       [:td (:station record)]
       [:td (:province record)]
       [:td (:start-date record)]
       [:td (:end-date record)]
       [:td (:sr90-activity record)]
       [:td (:sr90-error record)]
       [:td (:sr90-calcium record)]])]])
