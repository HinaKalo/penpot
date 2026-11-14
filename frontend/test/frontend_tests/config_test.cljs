;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; Copyright (c) KALEIDOS SUBSIDIARY SL

(ns frontend-tests.config-test
  (:require
   [app.common.flags :as flags]
   [app.config :as cf]
   [cljs.test :as t :include-macros true]))

(def ^:private apply-devenv-defaults @#'cf/apply-devenv-defaults)

(t/deftest devenv-default-disables-admin-console
  (t/testing "en devenv, sin flags del usuario, el admin-console queda desactivado"
    (t/is (not (contains? (flags/parse flags/default (apply-devenv-defaults true []))
                          :admin-console))))
  (t/testing "en devenv, el usuario puede reactivarlo con enable-admin-console"
    (t/is (contains? (flags/parse flags/default (apply-devenv-defaults true [:enable-admin-console]))
                     :admin-console)))
  (t/testing "en devenv, disable-admin-console del usuario es idempotente"
    (t/is (not (contains? (flags/parse flags/default (apply-devenv-defaults true [:disable-admin-console]))
                          :admin-console))))
  (t/testing "fuera de devenv no se inyecta nada: el default de flags aplica"
    (t/is (contains? (flags/parse flags/default (apply-devenv-defaults false []))
                     :admin-console))))
