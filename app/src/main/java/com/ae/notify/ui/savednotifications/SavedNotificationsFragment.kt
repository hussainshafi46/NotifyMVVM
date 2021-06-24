package com.ae.notify.ui.savednotifications

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ae.notify.R
import com.ae.notify.data.NotificationModel
import com.ae.notify.databinding.FragmentSavedNotificationsBinding
import com.ae.notify.service.NotifyListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNotificationsFragment : Fragment(R.layout.fragment_saved_notifications),
    SavedNotificationsAdapter.OnItemClickListener {

    private val viewModel: SavedNotificationsViewModel by viewModels()
    lateinit var binding: FragmentSavedNotificationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentSavedNotificationsBinding.bind(view)

        val recyclerAdapter = SavedNotificationsAdapter(requireContext(), this)
        binding.apply {
            recyclerView.apply {
                adapter = recyclerAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val notification = recyclerAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.rightSwipe(notification)
                }

            }).attachToRecyclerView(recyclerView)
        }
        viewModel.databaseContents.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notificationEvents.collect { event ->
                when (event) {
                    is SavedNotificationsViewModel.SavedNotificationEvents.NavigateToReadNotifications -> {
                        val action = SavedNotificationsFragmentDirections
                            .actionSavedNotificationsFragmentToReadNotificationFragment(event.notificationModel, event.notificationModel.sender)
                        findNavController().navigate(action)
                    }
                    is SavedNotificationsViewModel.SavedNotificationEvents.ShowUndoMsg -> {
                        Snackbar.make(requireView(), "Notification Deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.undoDelete(event.notificationModel)
                            }.show()
                    }
                    is SavedNotificationsViewModel.SavedNotificationEvents.DeleteAllConfirmMsg -> {
                        lateinit var dialog: AlertDialog
                        val alertDialogBuilder = AlertDialog.Builder(requireContext())
                            .setTitle("Warning")
                            .setMessage(event.message)
                            .setPositiveButton("Confirm") { a, b ->
                                viewModel.clearDatabaseConfirmed()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { a,b ->
                                dialog.dismiss()
                            }
                        dialog = alertDialogBuilder.create()
                        dialog.show()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAll -> {
                viewModel.deleteAllClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_saved_notification_fragment, menu)
    }

    override fun onItemClick(notificationModel: NotificationModel) {
        viewModel.readNotification(notificationModel)
    }

}