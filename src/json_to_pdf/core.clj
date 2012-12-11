(ns json-to-pdf.core
  (:require clj-pdf.core [clojure.data.json :only read])
  (:gen-class
    :name org.yogthos.JsonPDF
    :methods [#^{:static true} [writePDFToFile [String String] void]
              #^{:static true} [writePDFToStream [java.io.InputStream java.io.OutputStream] void]]))


(defn -writePDFToStream [^java.io.InputStream in ^java.io.OutputStream out]
  (with-open [r (new java.io.PushbackReader (clojure.java.io/reader in))]
    (clj-pdf.core/to-pdf (fn [r] (clojure.data.json/read r :eof-error? false)) r out)))


(defn -writePDFToFile [^String json ^String filename]
    (-writePDFToStream (new java.io.ByteArrayInputStream (.getBytes json)) (new java.io.FileOutputStream filename)))
