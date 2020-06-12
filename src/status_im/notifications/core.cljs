(ns status-im.notifications.core
  (:require [re-frame.core :as re-frame]
            [status-im.native-module.core :as status]
            ["react-native-push-notification" :as rn-pn]
            [quo.platform :as platform]
            [status-im.utils.utils :as utils]))

(defn enable-ios-notifications []
  (.configure
   ^js rn-pn
   (clj->js {:onRegister     (fn [token-data]
                               ;;TODO register token in status pn node send waku message
                               ;; it seems like there is not way to get this token again, so we have to store it in status-react
                               (let [token (.-token ^js token-data)]
                                 (utils/show-popup nil
                                                   (str "Token " token)
                                                   #())
                                 (println "TOKEN " token)))})))

(defn disable-ios-notifications []
  (println "hey" (.-abandonPermissions ^js rn-pn))
  (.abandonPermissions ^js rn-pn))
;;TODO remove token from status pn node, send waku message)

(re-frame/reg-fx
 ::enable
 (fn [_]
   (if platform/android?
     (status/enable-notifications)
     (enable-ios-notifications))))


(re-frame/reg-fx
 ::disable
 (fn [_]
   (if platform/android?
     (status/disable-notifications)
     (disable-ios-notifications))))
