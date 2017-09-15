(ns wscljs.client
  (:require [wscljs.format :as fmt]
            [wscljs.spec :as ws-spec]
            [cljs.spec.alpha :as s]))


(defn status [socket]
  "Retrieves the connection status of the socket."
  (condp = (.-readyState socket)
    0 :connecting
    1 :open
    2 :stopping
    3 :stopped))

(defn create
  "Starts a websocket connection and returns it.

  Takes the following arguments:

  url         => the websocket url
  handler-map => a hashmap containing handler functions mapping to:

                 - :on-open    => called when opening a socket connection
                 - :on-message => called when recieving message on the socket
                 - :on-close   => called when closing a socket connection

  Usage:

  (require '[wscljs.client :as ws]
           '[wscljs.format :as fmt])


  (def socket (ws/create \"ws://....\" handler-map))

  (ws/send socket data fmt/json)
  "
  [url {:keys [on-open on-message on-close] :as handler-map}]
  {:pre [(s/valid? ::ws-spec/websocket-handler-map handler-map)]}
  (if-let [sock (js/WebSocket. url)]
    (do
      (set! (.-onopen sock) on-open)
      (set! (.-onmessage sock) on-message)
      (set! (.-onclose sock) on-close)
      sock)
    (throw (js/Error. (str "Web socket connection failed: " url)))))

(defn send
  "Sends data over socket in the specified format."
  ([socket data]
   (send socket data fmt/identity))
  ([socket data format]
   {:pre [(s/valid? ::ws-spec/websocket-open socket)]}
   (.send socket (fmt/write format data))))

(defn close
  "Closes the socket connection."
  [socket]
  (.close socket))
