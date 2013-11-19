(defproject json-to-pdf "0.6.5"
  :description "a JVM library for generating PDF documents given JSON markup"
  :url "https://github.com/yogthos/json-to-pdf"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-pdf "1.11.6"]
                 [org.clojure/data.json "0.2.0"]]
  :aot  [json-to-pdf.core])
