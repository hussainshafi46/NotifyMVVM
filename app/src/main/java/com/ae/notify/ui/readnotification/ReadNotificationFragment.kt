package com.ae.notify.ui.readnotification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.ae.notify.R
import com.ae.notify.databinding.FragmentReadNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadNotificationFragment : Fragment(R.layout.fragment_read_notification) {

    private val viewModel: ReadNotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentReadNotificationBinding.bind(view)

        binding.apply {
            sender.text = viewModel.model?.sender
            message.text = viewModel.model?.message
        }
    }
}