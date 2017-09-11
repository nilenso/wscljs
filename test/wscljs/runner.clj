(ns wscljs.runner
  (:require [org.httpkit.server :as httpkit]
            [doo.core :as doo]))


(def compiler-opts
  {:output-to "resources/public/js/compiled/test.js"
   :output-dir "resources/public/js/compiled/test/out"
   :main 'wscljs.runner
   :target :phantom
   :optimizations :none})

(defn echo-handler [request]
  (httpkit/with-channel request channel
    (httpkit/on-receive channel (fn [data] (httpkit/send! channel data)))))

(defn run-server []
  (httpkit/run-server echo-handler {:port 3200}))

(defn run-tests []
  (doo/run-script :phantom compiler-opts))

(defn -main []
  (println "Starting server")
  (let [stop-server (run-server)]
    (println "Running tests")
    (run-tests)
    (println "Stopping server")
    (stop-server)
    (shutdown-agents)))
