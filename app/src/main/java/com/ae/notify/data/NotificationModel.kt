package com.ae.notify.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "notification_table")
data class NotificationModel(
    val sender: String,
    val message: String,
    val appPackage: String,
    val timestamp: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {

    val formattedTimeStamp: String
        get() = DateFormat.getDateTimeInstance().format(timestamp)

}