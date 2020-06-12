(ns status-im.notifications.core
  (:require [re-frame.core :as re-frame]
            [status-im.native-module.core :as status]
            ["react-native-push-notification" :as rn-pn]
            [quo.platform :as platform]))

(defn enable-ios-notifications []
  (.configure
   ^js rn-pn
   #js {:onRegister     (fn [token-data]
                          ;;TODO register token in status pn node send waku message
                          (let [token (.-token ^js token-data)]
                            (println "TOKEN " token)))
        :onNotification (fn [notification])                 ;notification.finish(PushNotificationIOS.FetchResult.NoData);
        :permissions    {:alert true
                         :badge true
                         :sound true
                         :popInitialNotification true
                         :requestPermissions true}}))

(defn disable-ios-notifications [])
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
