(defproject json-to-pdf "0.6.9"
  :description "a JVM library for generating PDF documents given JSON markup"
  :url "https://github.com/yogthos/json-to-pdf"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-pdf "2.0.0"]
                 [cheshire "5.4.0"]
                 [org.apache.xmlgraphics/batik-script "1.7"]]
  :aot  [json-to-pdf.core])
