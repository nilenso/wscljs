(ns wscljs.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [wscljs.client-test]))


(doo-tests 'wscljs.client-test)
