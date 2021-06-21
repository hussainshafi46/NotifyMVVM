package com.ae.notify.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
data class NotificationModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String?,
    val message: String?
    )