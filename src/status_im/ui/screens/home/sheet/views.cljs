(ns status-im.ui.screens.home.sheet.views
  (:require [re-frame.core :as re-frame]
            [status-im.ui.components.react :as react]
            [status-im.ui.components.list-item.views :as list-item]
            [status-im.utils.config :as config]
            [status-im.ui.components.invite.views :as invite]))

(defn hide-sheet-and-dispatch [event]
  (re-frame/dispatch [:bottom-sheet/hide-sheet])
  (re-frame/dispatch event))

(defn add-new-view []
  [react/view
   [list-item/list-item
    {:theme               :action
     :title               :t/start-new-chat
     :accessibility-label :start-1-1-chat-button
     :icon                :main-icons/one-on-one-chat
     :on-press            #(hide-sheet-and-dispatch [:navigate-to :new-chat])}]
   (when config/group-chat-enabled?
     [list-item/list-item
      {:theme               :action
       :title               :t/start-group-chat
       :accessibility-label :start-group-chat-button
       :icon                :main-icons/group-chat
       :on-press            #(hide-sheet-and-dispatch [:contact.ui/start-group-chat-pressed])}])
   [list-item/list-item
    {:theme               :action
     :title               :t/new-public-group-chat
     :accessibility-label :join-public-chat-button
     :icon                :main-icons/public-chat
     :on-press            #(hide-sheet-and-dispatch [:navigate-to :new-public-chat])}]
   [invite/list-item
    {:accessibility-label :chats-menu-invite-friends-button}]])

(def add-new
  {:content add-new-view})
