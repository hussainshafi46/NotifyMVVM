package com.ae.notify.ui.readnotification

import android.content.pm.PackageManager
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
        val pm = requireContext().packageManager
        binding.apply {
            message.text = viewModel.model?.message
            timestamp.text = viewModel.model?.formattedTimeStamp
            if(viewModel.model != null) {
                appName.text = pm.getApplicationLabel(pm.getApplicationInfo(viewModel.model!!.appPackage, PackageManager.GET_META_DATA))
            }
        }
    }
}