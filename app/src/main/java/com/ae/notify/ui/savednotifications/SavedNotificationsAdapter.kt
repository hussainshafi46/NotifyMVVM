package com.ae.notify.ui.savednotifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ae.notify.data.NotificationModel
import com.ae.notify.databinding.RecyclerViewTemplateBinding

class SavedNotificationsAdapter(private val listener: OnItemClickListener): ListAdapter<NotificationModel,SavedNotificationsAdapter.SavedNotificationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNotificationViewHolder {
        val binding = RecyclerViewTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedNotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedNotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(notificationModel: NotificationModel)
    }

    inner class SavedNotificationViewHolder(private val binding: RecyclerViewTemplateBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener.onItemClick(getItem(position))
            }
        }
        fun bind(notificationModel: NotificationModel) {
            binding.apply {
                sender.text = notificationModel.sender
                message.text = notificationModel.message
            }
        }
    }
    class DiffCallback: DiffUtil.ItemCallback<NotificationModel>() {
        override fun areItemsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ) = oldItem == newItem
    }

}