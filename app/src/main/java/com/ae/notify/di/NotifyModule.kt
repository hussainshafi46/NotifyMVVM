package com.ae.notify.di

import android.app.Application
import androidx.room.Room
import com.ae.notify.data.NotificationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotifyModule {
    @Provides
    @Singleton
    fun providesDatabase(app: Application) = Room.databaseBuilder(app,
                NotificationDatabase::class.java, "notify_db").build()

    @Provides
    fun providesNotificationDao(db: NotificationDatabase) = db.getNotificationDao()
}