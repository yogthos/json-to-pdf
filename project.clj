(defproject json-to-pdf "0.7.9"
  :description "a JVM library for generating PDF documents given JSON markup"
  :url "https://github.com/yogthos/json-to-pdf"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-pdf "2.2.22"]
                 [cheshire "5.7.0"]
                 [org.apache.xmlgraphics/batik-script "1.7"]]
  :aot :all)
