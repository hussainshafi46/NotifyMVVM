package com.ae.notify.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notificationModel: NotificationModel): Long

    @Delete
    suspend fun delete(notificationModel: NotificationModel)

    @Query("delete from notification_table")
    suspend fun clearDatabase()

    @Query("select * from notification_table order by timestamp desc")
    fun getSavedNotifications(): Flow<List<NotificationModel>>
}