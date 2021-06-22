package com.ae.notify.service

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
class NotifyListener: NotificationListenerService() {

    @Inject
    lateinit var notifyDao: NotificationDao

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val notification = sbn?.notification
        if (notification != null) {
            val bundle = notification.extras
            val title = bundle.getCharSequence("android.title").toString()
            val text = bundle.getCharSequence("android.text").toString()
            val testModel = NotificationModel(title, text)
            GlobalScope.launch {
                Log.d(TAG, "Insert Result: ${notifyDao.insert(testModel)}")
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