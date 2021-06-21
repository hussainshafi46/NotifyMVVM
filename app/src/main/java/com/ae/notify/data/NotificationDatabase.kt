package com.ae.notify.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NotificationModel::class], version = 1, exportSchema = false)
abstract class NotificationDatabase: RoomDatabase() {
    abstract fun getNotificationDao(): NotificationDao
}