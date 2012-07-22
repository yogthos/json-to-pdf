(defproject json-to-pdf "0.6.1-SNAPSHOT"
  :description "a JVM library for generating PDF documents given JSON markup"
  :url "https://github.com/yogthos/json-to-pdf"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-pdf "0.9.7"]
                 [org.clojure/data.json "0.1.2"]]
  :aot  [json-to-pdf.core])
