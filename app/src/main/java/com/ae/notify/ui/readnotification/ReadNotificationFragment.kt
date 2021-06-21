package com.ae.notify.ui.readnotification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ae.notify.R
import com.ae.notify.ui.ReadNotificationsViewModel


class ReadNotificationFragment : Fragment(R.layout.fragment_read_notification) {
    private val viewModel: ReadNotificationsViewModel by viewModels()
}