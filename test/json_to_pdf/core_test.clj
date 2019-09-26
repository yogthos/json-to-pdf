(ns json-to-pdf.core-test
  (:require [clojure.test :refer :all]
            [json-to-pdf.core :refer :all]
            [cheshire.core :refer [generate-string]])
  (:import
   com.lowagie.text.pdf.PdfPageEventHelper))

(comment
  (-writeToFile
    (generate-string [{} [:paragraph "Hello"]])

    "test.pdf"

    (proxy [PdfPageEventHelper] []
      (onStartPage [writer doc]
        (println "starting page")
        (.addImage (.getDirectContent writer)
                   (doto
                       (com.lowagie.text.Image/getInstance
                         (.getPath (new java.io.File (str "test" java.io.File/separator "mandelbrot.jpg"))))
                     (.scaleToFit 300 300)
                     (.setAbsolutePosition 100 100)))
        (println "page start:" doc))))

  )
