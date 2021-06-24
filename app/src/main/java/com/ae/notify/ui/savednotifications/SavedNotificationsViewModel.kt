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

    fun rightSwipe(notification: NotificationModel) = viewModelScope.launch {
        notifyDao.delete(notification)
        notificationEventsChannel.send(SavedNotificationEvents.ShowUndoMsg(notification))
    }

    fun undoDelete(notificationModel: NotificationModel) = viewModelScope.launch {
        notifyDao.insert(notificationModel)
    }

    fun deleteAllClicked() = viewModelScope.launch {
        notificationEventsChannel.send(SavedNotificationEvents.DeleteAllConfirmMsg("Are you sure you want to delete all saved notifications?"))
    }

    fun clearDatabaseConfirmed() = viewModelScope.launch {
        notifyDao.clearDatabase()
    }

    sealed class SavedNotificationEvents {
        data class NavigateToReadNotifications(val notificationModel: NotificationModel): SavedNotificationEvents()
        data class ShowUndoMsg(val notificationModel: NotificationModel): SavedNotificationEvents()
        data class DeleteAllConfirmMsg(val message: String): SavedNotificationEvents()
    }
}