(ns wscljs.spec
  (:require [cljs.spec.alpha :as s]))

(defn not-nil? [x] (not (nil? x)))

(s/def ::websocket-open (s/and not-nil?
                               #(= 1 (.-readyState %))))

(s/def ::on-message not-nil?)

(s/def ::websocket-handler-map
  (s/keys :req-un [::on-message]
          :opt-un [::on-open
                   ::on-close
                   ::on-error]))
