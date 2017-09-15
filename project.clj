(defproject nilenso/wscljs "0.1.0"
  :description "A thin and lightweight websocket client for ClojureScript."
  :url "https://github.com/nilenso/wscljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]]

  :plugins [[lein-figwheel "0.5.13"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-doo "0.1.7"]]

  :source-paths ["src"]

  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :sign-releases false}]]

  :aliases {"test" ["run" "-m" "wscljs.runner"]}

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {:on-jsload "wscljs.core/on-js-reload"
                           :open-urls ["http://localhost:3449/index.html"]}

                :compiler {:main wscljs.client
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/wscljs.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}

               {:id           "test"
                :source-paths ["test"]
                :compiler     {:main          wscljs.runner
                               :output-to     "resources/public/js/compiled/test.js"
                               :output-dir    "resources/public/js/compiled/test/out"
                               :target :phantom
                               :optimizations :none
                               :process-shim  false}}

               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/wscljs.js"
                           :main wscljs.client
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.4"]
                                  [figwheel-sidecar "0.5.13"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [org.clojure/core.async  "0.3.443"]
                                  [lein-doo "0.1.7"]
                                  [http-kit "2.2.0"]]
                   :source-paths ["src" "dev"]
                   :prep-tasks   ["compile" ["cljsbuild" "once"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
