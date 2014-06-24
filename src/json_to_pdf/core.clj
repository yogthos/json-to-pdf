(ns json-to-pdf.core
  (:require [clj-pdf.core :refer [pdf]]
            [cheshire.core :refer [parse-string]])
  (:import java.io.OutputStream)
  (:gen-class
   :name org.yogthos.JsonPDF
   :methods [#^{:static true} [writeToFile [String String] void]
             #^{:static true} [writeToStream [String java.io.OutputStream] void]]))

(defn -writeToFile [^String json ^String filename]
 (pdf (parse-string json true) filename))

(defn -writeToStream [^String json ^OutputStream out]
 (pdf (parse-string json true) out))
