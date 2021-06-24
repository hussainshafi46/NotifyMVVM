package com.ae.notify.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.ae.notify.data.NotificationDao
import com.ae.notify.data.NotificationModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotifyListener : NotificationListenerService() {

    @Inject
    lateinit var notifyDao: NotificationDao

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val notification = sbn?.notification

        if (notification != null) {
            if (notification.flags and Notification.FLAG_GROUP_SUMMARY !=0 )
                return
            val bundle = notification.extras
            if (bundle.containsKey(Notification.EXTRA_TITLE) && bundle.containsKey(Notification.EXTRA_TEXT)) {
                val title = bundle.get(Notification.EXTRA_TITLE).toString()
                val text = bundle.get(Notification.EXTRA_TEXT).toString()

                // "null" check for spamming apps like snapchat
                if (title == "null" || text == "null")
                    return

                val testModel = NotificationModel(title, text, sbn.packageName)
                GlobalScope.launch {
                    Log.d(TAG, "Insert Result: ${notifyDao.insert(testModel)}")
                }
                this.cancelNotification(sbn.key)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }

    companion object {
        private const val TAG = "TESTING"
    }
}