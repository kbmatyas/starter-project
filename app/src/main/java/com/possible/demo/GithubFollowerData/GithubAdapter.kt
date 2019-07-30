package com.possible.demo.GithubFollowerData

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.GithubLoginDataContainer
import com.possible.demo.R

class GithubAdapter: RecyclerView.Adapter<GithubAdapter.ViewHolder>() {

    private var githubFollowers: List<GithubLoginDataContainer> = ArrayList()
    private var context: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.github_user_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = githubFollowers.size

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.bindItems(githubFollowers[position], context)
    }


    fun setGithubFollowerList(followers: List<GithubLoginDataContainer>) {
        githubFollowers = listOf()
        notifyDataSetChanged()

        githubFollowers = followers
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var username: TextView = itemView.findViewById(R.id.username)

        fun bindItems(githubLoginDataContainer: GithubLoginDataContainer?, context: Context?) {
            username.text = githubLoginDataContainer?.login
        }
    }
}