package com.ae.notify.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notificationModel: NotificationModel): Long

    @Query("select * from notification_table")
    fun getSavedNotifications(): Flow<List<NotificationModel>>
}