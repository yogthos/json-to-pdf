(ns json-to-pdf.core
  (:require [clj-pdf.core :refer [pdf]]
            [clojure.walk :refer [prewalk]]
            [cheshire.core :refer [parse-string parse-stream]]
            [clojure.java.io :refer [reader]])
  (:import [java.io InputStream OutputStream]
           com.lowagie.text.pdf.PdfPageEventHelper)
  (:gen-class
   :name org.yogthos.JsonPDF
   :methods [#^{:static true} [writeToFile [String String com.lowagie.text.pdf.PdfPageEventHelper] void]
             #^{:static true} [writeToStream [java.io.InputStream java.io.OutputStream com.lowagie.text.pdf.PdfPageEventHelper] void]]))

(defn fix-pagebreaks [doc]
  (vec (prewalk #(if (= % ["pagebreak"]) [:pagebreak] %) doc)))

(defn parse [json parser out event-handler]
  (-> json
      (parser true)
      fix-pagebreaks
      (update-in [0] assoc :event-handler event-handler)
      (pdf out)))

(defn -writeToFile
  [^String json
   ^String filename
   ^PdfPageEventHelper eventHandler]
  (parse json parse-string filename eventHandler))

(defn -writeToStream
  [^InputStream json
   ^OutputStream out
   ^PdfPageEventHelper eventHandler]
  (parse json #(vec (parse-stream (reader %1) %2)) out eventHandler))
