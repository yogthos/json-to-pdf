(defproject json-to-pdf "0.8.1"
  :description "a JVM library for generating PDF documents given JSON markup"
  :url "https://github.com/yogthos/json-to-pdf"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-pdf "2.2.34"]
                 [cheshire "5.8.1"]
                 [org.apache.xmlgraphics/batik-script "1.7"]]
  :aot :all)
