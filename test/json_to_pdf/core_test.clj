(ns json-to-pdf.core-test
  (:use clojure.test
        json-to-pdf.core)
  (:require [clojure.data.json :only read-str]))

(binding [clojure.pprint/*print-right-margin* 100]
   (clojure.pprint/pprint 
     (clojure.data.json/read-str
  "[{\"right-margin\":50,
   \"subject\":\"Some subject\",
   \"creator\":\"Jane Doe\",
   \"doc-header\":[\"inspired by\", \"William Shakespeare\"],
   \"bottom-margin\":25,
   \"author\":\"John Doe\",
   \"header\":\"page header\",
   \"left-margin\":10,
   \"title\":\"Test doc\",
   \"size\":\"a4\",
   \"footer\":\"page\",
   \"top-margin\":20},
   
   [\"heading\", \"Lorem Ipsum\"],
   
   [\"paragraph\", \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec non iaculis lectus. Integer vel libero libero. Phasellus metus augue, consequat a viverra vel, fermentum convallis sem. Etiam venenatis laoreet quam, et adipiscing mi lobortis sit amet. Fusce eu velit vitae dolor vulputate imperdiet. Suspendisse dui risus, mollis ut tempor sed, dapibus a leo. Aenean nisi augue, placerat a cursus eu, convallis viverra urna. Nunc iaculis pharetra pretium. Suspendisse sit amet erat nisl, quis lacinia dolor. Integer mollis placerat metus in adipiscing. Fusce tincidunt sapien in quam vehicula tincidunt. Integer id ligula ante, interdum sodales enim. Suspendisse quis erat ut augue porta laoreet.\"],
   
   [\"paragraph\", \"Sed pellentesque lacus vel sapien facilisis vehicula. Quisque non lectus lacus, at varius nibh. Integer porttitor porttitor gravida. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan ante tincidunt magna dictum vulputate. Maecenas suscipit commodo leo sed mattis. Morbi dictum molestie justo eu egestas. Praesent lacus est, euismod vitae consequat non, accumsan in justo. Nam rhoncus dapibus nunc vel dignissim.\"],
   
   [\"paragraph\", \"Nulla id neque ac felis tempor pretium adipiscing ac tortor. Aenean ac metus sapien, at laoreet quam. Vivamus id dui eget neque mattis accumsan. Aliquam aliquam lacinia lorem ut dapibus. Fusce aliquam augue non libero viverra ut porta nisl mollis. Mauris in justo in nibh fermentum dapibus at ut erat. Maecenas vitae fermentum lectus. Nunc dolor nisl, commodo a pellentesque non, tincidunt id dolor. Nulla tellus neque, consectetur in scelerisque vitae, cursus vel urna. Phasellus ullamcorper ultrices nisi ac feugiat.\"],
   
   
   [\"table\",
   {\"header\":[{\"color\":[100, 100, 100]}, \"FOO\"], \"cellSpacing\":20},
   [\"foo\",
    [\"cell\",
     [\"phrase\",
      {\"style\":\"italic\",
       \"size\":18,
       \"family\":\"halvetica\",
       \"color\":[200, 55, 221]},
      \"Hello Clojure!\"]],
    \"baz\"],
   [\"foo1\", [\"cell\", {\"color\":[100, 10, 200]}, \"bar1\"], \"baz1\"],
   [\"foo2\", \"bar2\",
    [\"cell\",
     [\"table\",
      [\"Inner table Col1\", \"Inner table Col2\", \"Inner table Col3\"]]]]],
   
   [\"paragraph\", \"Suspendisse consequat, mauris vel feugiat suscipit, turpis metus semper metus, et vulputate sem nisi a dolor. Duis egestas luctus elit eget dignissim. Vivamus elit elit, blandit id volutpat semper, luctus id eros. Duis scelerisque aliquam lorem, sed venenatis leo molestie ac. Vivamus diam arcu, sodales at molestie nec, pulvinar posuere est. Morbi a velit ante. Nulla odio leo, volutpat vitae eleifend nec, luctus ac risus. In hac habitasse platea dictumst. In posuere ultricies nulla, eu interdum erat rhoncus ac. Vivamus rutrum porta interdum. Nulla pulvinar dui quis velit varius tristique dignissim sem luctus. Aliquam ac velit enim. Sed sed nisi faucibus ipsum congue lacinia. Morbi id mi in lectus vehicula dictum vel sed metus. Sed commodo lorem non nisl vulputate elementum. Fusce nibh dui, auctor a rhoncus eu, rhoncus eu eros.\"],
    
   [\"paragraph\", \"Nulla pretium ornare nisi at pulvinar. Praesent lorem diam, pulvinar nec scelerisque et, mattis vitae felis. Integer eu justo sem, non molestie nisl. Aenean interdum erat non nulla commodo pretium. Quisque egestas ullamcorper lacus id interdum. Ut scelerisque, odio ac mollis suscipit, libero turpis tempus nulla, placerat pretium tellus purus eu nunc. Donec nec nisi non sem vehicula posuere et eget sem. Aliquam pretium est eget lorem lacinia in commodo nisl laoreet. Curabitur porttitor dignissim eros, nec semper neque tempor non. Duis elit neque, sagittis vestibulum consequat ut, rhoncus sed dui.\"],
           
        
  [\"anchor\", {\"style\":{\"size\":15}, \"leading\":20}, \"some anchor\"],
  
  [\"anchor\", [\"phrase\", {\"style\":\"bold\"}, \"some anchor phrase\"]],
  
  [\"anchor\", \"plain anchor\"],
  
  [\"chunk\", {\"style\":\"bold\"}, \"small chunk of text\"],
  
  [\"phrase\", \"some text here\"],
  
  [\"phrase\",
   {\"style\":\"italic\",
    \"size\":18,
    \"family\":\"halvetica\",
    \"color\":[0, 255, 221]},
   \"Hello Clojure!\"],
   
   [\"chapter\", [\"paragraph\", \"Second Chapter\"]],
   
   [\"paragraph\", {\"keep-together\":true, \"indent\":20},
   \"a fine paragraph\"],
   
   [\"list\", {\"roman\":true}, [\"chunk\", {\"style\":\"bold\"}, \"a bold item\"],
   \"another item\", \"yet another item\"],
   
   [\"chapter\", \"Charts\"],
   
   [\"chart\",
   {\"type\":\"bar-chart\",
    \"title\":\"Bar Chart\",
    \"x-label\":\"Items\",
    \"y-label\":\"Quality\"},
   [2, \"Foo\"], [4, \"Bar\"], [10, \"Baz\"]],
   
   [\"chart\",
   {\"type\":\"line-chart\",
    \"title\":\"Line Chart\",
    \"x-label\":\"checkpoints\",
    \"y-label\":\"units\"},
   [\"Foo\", [1, 10], [2, 13], [3, 120], [4, 455], [5, 300], [6, 600]],
   [\"Bar\", [1, 13], [2, 33], [3, 320], [4, 155], [5, 200], [6, 300]]],
   
   [\"chart\", {\"type\":\"pie-chart\", \"title\":\"Big Pie\"}, [\"One\", 21],
   [\"Two\", 23], [\"Three\", 345]]
   
   [\"chart\",
   {\"type\":\"line-chart\",
    \"time-series\":true,
    \"title\":\"Time Chart\",
    \"x-label\":\"time\",
    \"y-label\":\"progress\"},
   [\"Incidents\", [\"2011-01-03-11:20:11\", 200],
    [\"2011-01-03-11:25:11\", 400], [\"2011-01-03-11:35:11\", 350],
    [\"2011-01-03-12:20:11\", 600]]]]"
  :key-fn keyword)))




(pdf 
  [{:title         "Test doc"
    :header        "page header"
    :subject       "Some subject"  
    :creator       "Jane Doe"
    :doc-header    ["inspired by" "William Shakespeare"]
    :right-margin  50
    :author        "John Doe"
    :bottom-margin 25
    :left-margin   10    
    :top-margin    20
    :size          "a4"
    :footer        "page"}
   
   ["heading" "Lorem Ipsum"]
   
   ["paragraph"
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec non iaculis lectus. Integer vel libero libero. Phasellus metus augue, consequat a viverra vel, fermentum convallis sem. Etiam venenatis laoreet quam, et adipiscing mi lobortis sit amet. Fusce eu velit vitae dolor vulputate imperdiet. Suspendisse dui risus, mollis ut tempor sed, dapibus a leo. Aenean nisi augue, placerat a cursus eu, convallis viverra urna. Nunc iaculis pharetra pretium. Suspendisse sit amet erat nisl, quis lacinia dolor. Integer mollis placerat metus in adipiscing. Fusce tincidunt sapien in quam vehicula tincidunt. Integer id ligula ante, interdum sodales enim. Suspendisse quis erat ut augue porta laoreet."]
   
   ["paragraph"
    "Sed pellentesque lacus vel sapien facilisis vehicula. Quisque non lectus lacus, at varius nibh. Integer porttitor porttitor gravida. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan ante tincidunt magna dictum vulputate. Maecenas suscipit commodo leo sed mattis. Morbi dictum molestie justo eu egestas. Praesent lacus est, euismod vitae consequat non, accumsan in justo. Nam rhoncus dapibus nunc vel dignissim."]
   
   ["paragraph"
    "Nulla id neque ac felis tempor pretium adipiscing ac tortor. Aenean ac metus sapien, at laoreet quam. Vivamus id dui eget neque mattis accumsan. Aliquam aliquam lacinia lorem ut dapibus. Fusce aliquam augue non libero viverra ut porta nisl mollis. Mauris in justo in nibh fermentum dapibus at ut erat. Maecenas vitae fermentum lectus. Nunc dolor nisl, commodo a pellentesque non, tincidunt id dolor. Nulla tellus neque, consectetur in scelerisque vitae, cursus vel urna. Phasellus ullamcorper ultrices nisi ac feugiat."]
   
   ["table" {:header [{:color [100 100 100]} "FOO"] :cellSpacing 20}
    ["foo"
     ["cell"
      ["phrase"
       {:style "italic" :size 18 :family "halvetica" :color [200 55 221]}
       "Hello Clojure!"]]   
     "baz"]
    ["foo1" ["cell" {:color [100 10 200]} "bar1"] "baz1"]
    ["foo2" "bar2" ["cell" ["table" ["Inner table Col1" "Inner table Col2" "Inner table Col3"]]]]]
   
   ["paragraph"
    "Suspendisse consequat, mauris vel feugiat suscipit, turpis metus semper metus, et vulputate sem nisi a dolor. Duis egestas luctus elit eget dignissim. Vivamus elit elit, blandit id volutpat semper, luctus id eros. Duis scelerisque aliquam lorem, sed venenatis leo molestie ac. Vivamus diam arcu, sodales at molestie nec, pulvinar posuere est. Morbi a velit ante. Nulla odio leo, volutpat vitae eleifend nec, luctus ac risus. In hac habitasse platea dictumst. In posuere ultricies nulla, eu interdum erat rhoncus ac. Vivamus rutrum porta interdum. Nulla pulvinar dui quis velit varius tristique dignissim sem luctus. Aliquam ac velit enim. Sed sed nisi faucibus ipsum congue lacinia. Morbi id mi in lectus vehicula dictum vel sed metus. Sed commodo lorem non nisl vulputate elementum. Fusce nibh dui, auctor a rhoncus eu, rhoncus eu eros."]
   
   ["paragraph"
    "Nulla pretium ornare nisi at pulvinar. Praesent lorem diam, pulvinar nec scelerisque et, mattis vitae felis. Integer eu justo sem, non molestie nisl. Aenean interdum erat non nulla commodo pretium. Quisque egestas ullamcorper lacus id interdum. Ut scelerisque, odio ac mollis suscipit, libero turpis tempus nulla, placerat pretium tellus purus eu nunc. Donec nec nisi non sem vehicula posuere et eget sem. Aliquam pretium est eget lorem lacinia in commodo nisl laoreet. Curabitur porttitor dignissim eros, nec semper neque tempor non. Duis elit neque, sagittis vestibulum consequat ut, rhoncus sed dui."]
   
   ["anchor" {:style {:size 15} :leading 20} "some anchor"]
   
   ["anchor" ["phrase" {:style "bold"} "some anchor phrase"]]
   
   ["anchor" "plain anchor"]
   
   ["chunk" {:style "bold"} "small chunk of text"]
   
   ["phrase" "some text here"]
   
   ["phrase" {:style "italic" :size 18 :family "halvetica" :color [0 255 221]} "Hello Clojure!"]
   
   ["chapter" ["paragraph" "Second Chapter"]]
   
   ["paragraph" {:keep-together true :indent 20} "a fine paragraph"]
   
   ["list" {:roman true} ["chunk" {:style "bold"} "a bold item"] "another item" "yet another item"]
   
   ["chapter" "Charts"]
   
   ["chart"
    {:type "bar-chart" :title "Bar Chart" :x-label "Items" :y-label "Quality"}
    [2 "Foo"]
    [4 "Bar"]
    [10 "Baz"]]
   
   ["chart"
    {:type "line-chart" :title "Line Chart" :x-label "checkpoints" :y-label "units"}
    ["Foo" [1 10] [2 13] [3 120] [4 455] [5 300] [6 600]]
    ["Bar" [1 13] [2 33] [3 320] [4 155] [5 200] [6 300]]]
   
   ["chart" {:type "pie-chart" :title "Big Pie"} ["One" 21] ["Two" 23] ["Three" 345]]
   
   ["chart"
    {:type "line-chart"
     :time-series true
     :title "Time Chart"
     :x-label "time"
     :y-label "progress"}
    ["Incidents"
     ["2011-01-03-11:20:11" 200]
     ["2011-01-03-11:25:11" 400]
     ["2011-01-03-11:35:11" 350]
     ["2011-01-03-12:20:11" 600]]]]
  "example.pdf")