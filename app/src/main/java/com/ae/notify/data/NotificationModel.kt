package com.ae.notify.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notification_table")
data class NotificationModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String?,
    val message: String?
    ): Parcelable