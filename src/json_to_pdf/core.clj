(ns json-to-pdf.core
  (:require [clj-pdf.core :refer [pdf]]
            [cheshire.core :refer [parse-string parse-stream]]
            [clojure.java.io :refer [reader]])
  (:import [java.io InputStream OutputStream])
  (:gen-class
   :name org.yogthos.JsonPDF
   :methods [#^{:static true} [writeToFile [String String] void]
             #^{:static true} [writeToStream [java.io.InputStream java.io.OutputStream] void]]))

(defn -writeToFile [^String json ^String filename]
 (pdf (parse-string json true) filename))

(defn -writeToStream [^InputStream json ^OutputStream out]
 (pdf (parse-stream (reader json) true) out))
