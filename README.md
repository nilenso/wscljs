# wscljs

[![CircleCI](https://circleci.com/gh/nilenso/wscljs.svg?style=svg)](https://circleci.com/gh/nilenso/wscljs) [![Clojars
Project](https://img.shields.io/clojars/v/nilenso/wscljs.svg)](https://clojars.org/nilenso/wscljs) [![Cljdoc badge](https://cljdoc.org/badge/nilenso/wscljs)](https://cljdoc.org/d/nilenso/wscljs/0.1.3/doc/readme)

A thin and lightweight(no external dependencies) websocket client for
ClojureScript.

## Why did we write this?

There are already existing Clojure/Clojurescript websocket libraries like [Sente](https://github.com/ptaoussanis/sente)
and [Chord](https://github.com/jarohen/chord). However these libraries support creating both websocket server and
client. This requires additional dependencies which we didn't want. Wscljs is a
thin wrapper over the Javascript websockets and brings in no extra
dependency.

## Usage

```clojure
(require '[wscljs.client :as ws]
```

To create a new websocket connection:

```clojure
(def socket (ws/create "ws://...." handlers))
```

where `handlers` is a map containing handler functions mapped to the following keys:

Required:

  - `:on-message` => called when recieving message on the socket

Optional:

  - `:on-open`    => called when opening a socket connection
  - `:on-close`   => called when closing a socket connection
  - `:on-error`   => called when an error is received

For example, to print the data received by the socket, do:
```clojure
(def handlers {:on-message (fn [e] (prn (.-data e)))
               :on-open    #(prn "Opening a new connection")
               :on-close   #(prn "Closing a connection")})
(def socket (ws/create "ws://...." handlers))
```
To send json data over the socket, do:

```clojure
(require '[wscljs.format :as fmt])

(ws/send socket {:command "ping"} fmt/json)
```

**The supported formats are:**

- `json`
- `edn`
- `identity`

After you're done, close the socket:

```clojure
(ws/close socket)
```

## Setup

To get an interactive development environment run:

```shell
lein figwheel
```

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

```clojure
(js/alert "Am I connected?")
```

and you should see an alert in the browser window.

To clean all compiled files:

```shell
lein clean
```

To create a production build run:

```shell
lein do clean, cljsbuild once min
```

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

## Testing

Inorder to run tests, you need to have [PhantomJS](http://phantomjs.org/)
installed. After installing it, run the tests:

```shell
lein test
```

## Authors

- Abhik Khanra (@trycatcher)
- Kiran Gangadharan (@kirang89)
- Udit Kumar (@yudistrange)

## License

Copyright © 2017 Nilenso Software  LLP

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
