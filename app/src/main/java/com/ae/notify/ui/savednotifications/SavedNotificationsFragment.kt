package com.ae.notify.ui.savednotifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ae.notify.R
import com.ae.notify.data.NotificationModel
import com.ae.notify.databinding.FragmentSavedNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNotificationsFragment : Fragment(R.layout.fragment_saved_notifications), SavedNotificationsAdapter.OnItemClickListener {

    private val viewModel: SavedNotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSavedNotificationsBinding.bind(view)

        val recyclerAdapter = SavedNotificationsAdapter(this)
        binding.apply {
            recyclerView.apply {
                adapter = recyclerAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.databaseContents.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notificationEvents.collect { event ->
                when(event) {
                    is SavedNotificationsViewModel.SavedNotificationEvents.NavigateToReadNotifications -> {
                        val action = SavedNotificationsFragmentDirections
                            .actionSavedNotificationsFragmentToReadNotificationFragment(event.notificationModel)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
    override fun onItemClick(notificationModel: NotificationModel) {
        viewModel.readNotification(notificationModel)
    }
}