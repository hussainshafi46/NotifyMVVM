package com.ae.notify.ui.savednotifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ae.notify.data.NotificationDao
import com.ae.notify.data.NotificationModel
import com.ae.notify.databinding.FragmentSavedNotificationsBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNotificationsViewModel @Inject constructor(
    private val notifyDao: NotificationDao
): ViewModel() {

    val databaseContents = notifyDao.getSavedNotifications().asLiveData()
    private val notificationEventsChannel = Channel<SavedNotificationEvents>()
    val notificationEvents = notificationEventsChannel.receiveAsFlow()

    fun readNotification(notificationModel: NotificationModel) = viewModelScope.launch {
        notificationEventsChannel.send(SavedNotificationEvents.NavigateToReadNotifications(notificationModel))
    }

    sealed class SavedNotificationEvents {
        data class NavigateToReadNotifications(val notificationModel: NotificationModel): SavedNotificationEvents()
    }
}