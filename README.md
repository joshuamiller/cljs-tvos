# cljs-tvos

`cljs-tvos` provides ClojureScript bindings for AppleTV's TVML and TVJS.

## Getting Started

Follow
[Apple's instructions for building a client-server AppleTV app](https://developer.apple.com/library/prerelease/tvos/documentation/General/Conceptual/AppleTV_PG/YourFirstAppleTVApp.html#//apple_ref/doc/uid/TP40015241-CH3-SW1),
and point your app to where your built ClojureScript will be served
from.

## Using cljs-tvos To Build An AppleTV App

Your app should start with a function that loads and displays a TVML
file, and that function should be called on the `App` object's
`onLaunch` event:

```clojure
(aset js/App "onLaunch" welcome-screen)
```

That `welcome-screen` function needs to fetch a [TVML document](https://developer.apple.com/library/prerelease/tvos/documentation/LanguagesUtilities/Conceptual/ATV_Template_Guide/), parse
it, and push it onto the
[`navigationDocument`](https://developer.apple.com/library/prerelease/tvos/documentation/TVMLJS/Reference/TVJSNavigationDocument_Ref/index.html#//apple_ref/javascript/cl/NavigationDocument)'s
stack.

```clojure
(defn welcome-screen
  []
  (GET "http://localhost:3000/index.tvml"
       {:handler (fn [resp]
                   (-> (tvjs/parse-document)
                       (tvjs/push-document)))}))
```

## Functionality

`cljs-tvos` provides convenience functions around TVML (in the
`cljs-tvos.tvml` namespace) and TVJS (in `cljs-tvos.tvjs`).

### DOM Manipulation

TVJS has a limited subset of typical browser DOM manipulation
functionality. `cljs-tvos.tvml` provides wrapper functions around
TVJS's DOM API: `(tvml/by-tag-name doc "listItemLockup")` will return
a TVJS `NodeList` of `<listItemLockup>` nodes. The `NodeList` type has
been extended with `ISeqable` (thanks to
[Domina](https://github.com/levand/domina)) so that traditional
Clojure `seq` functions like `map`, `for`, et al. are available.

### Storage

TVJS provides two different types of key-value storage: "local," which
persists to disk, and "session," which is limited to the app's life cycle.
`cljs-tvos` uses [Transit](https://github.com/cognitect/transit-cljs)
to preserve type information the values that are stored.

To store an item to `localStorage`: `(tvjs/store-item :countries
['Chile' 'Argentina' 'Uruguay'])`.

## License

Copyright © 2015 Joshua Miller

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
