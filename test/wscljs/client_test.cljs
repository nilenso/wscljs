(ns wscljs.client-test
  (:require-macros [cljs.core.async.macros :as m :refer [go]])
  (:require [cljs.test :refer-macros [deftest is testing async use-fixtures]]
            [wscljs.client :as ws]
            [wscljs.format :as fmt]
            [cljs.core.async :refer [chan close!]]))

(def wsurl "ws://localhost:3200/ws/")

(deftest fact
  (is (= 1 1)))

(defn timeout [ms]
  (let [c (chan)]
    (js/setTimeout (fn [] (close! c)) ms)
    c))

(deftest test-open
  (testing "Opening a socket connection"
    (async done
           (go
             (let [socket (ws/create wsurl {:on-message identity})]
               (is (= :connecting (ws/status socket)))
               (<! (timeout 1000))
               (is (= :open (ws/status socket)))
               (done))))))

(deftest test-close
  (testing "Closing a socket connection"
    (async done
           (go
             (let [socket (ws/create wsurl {:on-message identity})]
               (<! (timeout 1000))
               (ws/close socket)
               (is (= :stopping (ws/status socket)))
               (<! (timeout 1000))
               (is (= :stopped (ws/status socket)))
               (done))))))

(deftest test-send
  (testing "Sending data through the socket connection"
    (async done
           (go (let [socket (ws/create wsurl {:on-message identity})]
                 (<! (timeout 1000))
                 (ws/close socket)
                 (<! (timeout 1000))
                 (try
                   (ws/send socket {:command "ping"})
                   (catch js/Object e
                     (is (= "Assert failed: (s/valid? :wscljs.spec/websocket-open socket)"
                            (.-message e)))))
                 (done))))))

(deftest test-receive
  (testing "Receiving sent data over the socket connection"
    (async done
           (go (let [data "hello"
                     recvd-data (atom nil)
                     socket (ws/create wsurl {:on-message #(reset! recvd-data (.-data %))})]
                 (<! (timeout 1000))
                 (ws/send socket data fmt/identity)
                 (<! (timeout 1000))
                 (is (= data @recvd-data))
                 (done))))))


(deftest test-json
  (testing "Receiving sent data over the socket connection"
    (async done
           (go (let [data {:command "ping"}
                     format fmt/json
                     recvd-data (atom nil)
                     socket (ws/create wsurl {:on-message #(reset! recvd-data
                                                                  (fmt/read format (.-data %)))})]
                 (<! (timeout 1000))
                 (ws/send socket data format)
                 (<! (timeout 1000))
                 (is (= data @recvd-data))
                 (done))))))
