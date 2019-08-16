package com.possible.demo.features.repos

import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.databinding.ListItemRepoBinding

class ReposAdapter : ListAdapter<Repo, ReposAdapter.ViewHolder>(RepoDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo) {
            binding.repo = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemRepoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(p0: Repo, p1: Repo): Boolean {
        return p0.name == p1.name
    }

    override fun areContentsTheSame(p0: Repo, p1: Repo): Boolean {
        return p0 == p1
    }
}