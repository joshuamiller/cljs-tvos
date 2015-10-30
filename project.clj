(defproject cljs-tvos "0.1.0"
  :description "ClojureScript bindings for AppleTV's TVML and TVJS"
  :url "https://github.com/joshuamiller/cljs-tvos"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145" :classifier "aot"
                  :exclusion [org.clojure/data.json]]
                 [org.clojure/data.json "0.2.6" :classifier "aot"]
                 [com.cognitect/transit-cljs "0.8.225"]])
