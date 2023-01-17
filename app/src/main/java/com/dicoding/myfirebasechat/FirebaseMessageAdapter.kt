package com.dicoding.myfirebasechat

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myfirebasechat.databinding.ItemMessageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseMessageAdapter(
    options: FirebaseRecyclerOptions<MessageModel>,
    private val currentUsername: String?
) : FirebaseRecyclerAdapter<MessageModel, FirebaseMessageAdapter.MessageViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_message, parent, false)
        val binding = ItemMessageBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, message: MessageModel) {
        holder.bind(message)
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageModel) {
            if (currentUsername == item.name && item.name != null) {
                binding.tvMessageRight.text = item.text
                setTextColor(item.name, binding.tvMessageRight)
                binding.tvMessengerRight.text = item.name
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .circleCrop()
                    .into(binding.ivMessengerRight)

                if (item.timestamp != null) {
                    binding.tvTimestampRight.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
                }

                binding.apply {
                    tvMessage.alpha = 0F
                    tvMessenger.alpha = 0F
                    ivMessenger.alpha = 0F
                    tvTimestamp.alpha = 0F
                }
            } else {
                binding.tvMessage.text = item.text
                setTextColor(item.name, binding.tvMessage)
                binding.tvMessenger.text = item.name
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .circleCrop()
                    .into(binding.ivMessenger)

                if (item.timestamp != null) {
                    binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
                }

                binding.apply {
                    tvMessageRight.alpha = 0F
                    tvMessengerRight.alpha = 0F
                    ivMessengerRight.alpha = 0F
                    tvTimestampRight.alpha = 0F
                }
            }
        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (currentUsername == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_blue)
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_yellow)
            }
        }
    }
}