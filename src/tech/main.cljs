(ns tech.main
  (:require ["electron" :as e :refer (app BrowserWindow)]
            ["path" :as path]
            ["os" :as os]
            ["url" :as url]))

(defn start []
  (js/console.log "renderer - start")
  (js/console.log "renderer - start")
  (js/console.log "haha"))


(defn stop []
  (js/console.log "renderer - stop"))

(defonce win-ref (atom nil))

(defn create-window []
  (let [win
        (BrowserWindow.
         #js {:width 800
              :height 600
              :webPreferences
              #js { :nodeIntegration true}})

        url
        (url/format #js {:pathname (path/join js/__dirname "index.html")
                         :protocol "file:"
                         :slashes true})]

    (.loadURL win url)

    (reset! win-ref win)

    (.on win "closed"
         (fn [e]
           (reset! win-ref nil)))))

(defn maybe-quit []
  (when (not= js/process.platform "darwin")
    (.quit app)))

(defn maybe-create-window []
  (when-not @win-ref
    (create-window)))

(defn main []
  (.on app "ready" create-window)
  (.on app "activate" maybe-create-window)
  (.on app "window-all-closed" maybe-quit))
