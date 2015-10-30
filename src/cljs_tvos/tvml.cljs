(ns cljs-tvos.tvml)

(defn by-id
  "Returns an element from the document by its ID"
  [doc id]
  (.getElementById doc id))

(defn by-tag-name
  "Returns a NodeList of elements for a given tag name"
  [doc name]
  (.getElementsByTagName doc name))

(defn attr
  "Gets the value of an attribute on a node"
  [node attr-name]
  (.getAttribute node attr-name))

(defn data
  "Returns the value of the data-prefixed attribute, like jQuery's data()"
  [node key]
  (attr node (str "data-" key)))

(defn listen!
  "Adds a listener function to a node"
  [node event function]
  (.addEventListener node event function))
