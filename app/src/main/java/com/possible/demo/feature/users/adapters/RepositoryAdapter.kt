package com.possible.demo.feature.users.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.R
import com.possible.demo.RepositoryItemBinding
import com.possible.demo.data.model.Repository

class RepositoryAdapter : ListAdapter<Repository, RepositoryAdapter.RepoViewHolder>(RepositoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_repository, parent, false))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repository = getItem(position)
        holder.binding.repo = repository
        holder.binding.startCount.text = repository.stargazerCount.toString()
    }

    class RepoViewHolder(val binding: RepositoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {

        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem == newItem
    }
}
