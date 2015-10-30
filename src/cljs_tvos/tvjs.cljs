(ns cljs-tvos.tvjs
  (:require [cognitect.transit :as t]))

(defn store-item
  "Transit-encodes and stores an item in LocalStorage"
  [k v]
  (let [writer (t/writer :json)]
    (.setItem js/localStorage (name k) (t/write writer v))))

(defn retrieve-item
  "Retrieves an item stored in LocalStorage, deserializing with Transit"
  [k]
  (if-let [value (.getItem js/localStorage (name k))]
    (let [reader (t/reader :json)]
      (t/read reader value))))

(defn session-store-item
  "Transit-encodes and stores an item in SessionStorage"
  [k v]
  (let [writer (t/writer :json)]
    (.setItem js/sessionStorage (name k) (t/write writer v))))

(defn session-retrieve-item
  "Retrieves an item stored in SessionStorage, deserializing with Transit"
  [k]
  (if-let [value (.getItem js/sessionStorage (name k))]
    (let [reader (t/reader :json)]
      (t/read reader value))))

(defn parse-document
  "Instantiates a TVML Document from a String representation"
  [doc]
  (let [parser (js/DOMParser.)]
    (.parseFromString parser doc "application/xml")))

(defn push-document
  "Pushes a Document onto navigationDocument's stack"
  [doc]
  (.pushDocument js/navigationDocument doc))

(defn pop-document
  "Pops the top document from navigationDocument's stack"
  []
  (.popDocument js/navigationDocument))

(defn log
  "Logs the Javascript representation of a value to the console"
  [val]
  (.log js/console (clj->js val)))

; From Domina https://github.com/levand/domina

(defn- lazy-nl-via-item
  ([nl] (lazy-nl-via-item nl 0))
  ([nl n] (when (< n (. nl -length))
            (lazy-seq
             (cons (. nl (item n))
                   (lazy-nl-via-item nl (inc n)))))))

(defn- lazy-nl-via-array-ref
  ([nl] (lazy-nl-via-array-ref nl 0))
  ([nl n] (when (< n (. nl -length))
            (lazy-seq
             (cons (aget nl n)
                   (lazy-nl-via-array-ref nl (inc n)))))))

(defn- lazy-nodelist
  "A lazy seq view of a js/NodeList, or other array-like javascript things"
  [nl]
  (if (. nl -item)
    (lazy-nl-via-item nl)
    (lazy-nl-via-array-ref nl)))

(extend-type js/NodeList
  ICounted
  (-count [nodelist] (. nodelist -length))

  IIndexed
  (-nth ([nodelist n] (. nodelist (item n)))
    ([nodelist n not-found] (if (<= (. nodelist -length) n)
                              not-found
                              (nth nodelist n))))
  ISeqable
  (-seq [nodelist] (lazy-nodelist nodelist)))
