(ns json-to-pdf.core
  (:require [clj-pdf.core :refer [pdf]]
            [clojure.walk :refer [prewalk]]
            [cheshire.core :refer [parse-string parse-stream]]
            [clojure.java.io :refer [reader]])
  (:import [java.io InputStream OutputStream])
  (:gen-class
   :name org.yogthos.JsonPDF
   :methods [#^{:static true} [writeToFile [String String] void]
             #^{:static true} [writeToStream [java.io.InputStream java.io.OutputStream] void]]))

(defn fix-pagebreaks [doc]
  (prewalk #(if (= % ["pagebreak"]) [:pagebreak] %) doc))

(defn parse [json parser out]
  (-> json (parser true) fix-pagebreaks (pdf out)))

(defn -writeToFile [^String json ^String filename]
  (parse json parse-string filename))

(defn -writeToStream [^InputStream json ^OutputStream out]
  (parse json #(parse-stream (reader %1) %2) out))
