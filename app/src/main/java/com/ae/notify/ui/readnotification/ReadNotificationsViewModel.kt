package com.ae.notify.ui.readnotification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ae.notify.data.NotificationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadNotificationsViewModel @Inject constructor(
    saveState: SavedStateHandle
): ViewModel() {

    val model = saveState.get<NotificationModel>("notification")
}