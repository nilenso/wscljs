# wscljs

[![Clojars
Project](https://img.shields.io/clojars/v/nilenso/wscljs.svg)](https://clojars.org/nilenso/wscljs)

A thin and lightweight websocket client for ClojureScript.

## Usage

    (require '[wscljs.client :as ws]

To create a new websocket connection:

    (def socket (ws/create "ws://...." handlers))

where handlers is a map containing handler functions mapped to the following keys:

- `:on-open`    => called when opening a socket connection
- `:on-message` => called when recieving message on the socket
- `:on-close`   => called when closing a socket connection

To send data over the socket, do:

    (ws/send socket data encoding)

The available encodings are `identity` (raw/no encoding) and `json`

After you're done, close the socket using:

    (ws/close socket)

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

## Testing

To run the tests, do

    lein test

## Contributors

- Abhik Khanra (@trycatcher)
- Kiran Gangadharan (@kirang89)
- Udit Kumar (@yudistrange)

## License

Copyright © 2017 Nilenso Software  LLP

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
