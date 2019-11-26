package com.possible.demo.feature.users.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.R
import com.possible.demo.UserItemBinding
import com.possible.demo.data.model.User

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    var clickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user, parent, false),
            clickListener
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.user = getItem(position)
    }

    fun getItemOn(position: Int): User? = if (position in 0 until itemCount) getItem(position) else null

    class UserViewHolder(val binding: UserItemBinding, clickListener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { clickListener?.invoke(adapterPosition) }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
    }
}