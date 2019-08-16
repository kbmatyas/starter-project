package com.possible.demo.features.followers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.possible.demo.R
import com.possible.demo.databinding.FragmentFollowersBinding

class FollowersFragment : androidx.fragment.app.Fragment() {

    private lateinit var followersViewModel: FollowersViewModel
    lateinit var toReposFragmentCb: (login: String) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentFollowersBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_followers, container, false)

        followersViewModel =
                ViewModelProviders.of(
                        this).get(FollowersViewModel::class.java)
        lifecycle.addObserver(followersViewModel)

        binding.followersViewModel = followersViewModel

        val adapter = FollowersAdapter()
        binding.followersList.adapter = adapter

        followersViewModel.followers.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                adapter.submitList(list.toList())
            }
        })

        binding.setLifecycleOwner(this)

        followersViewModel.toReposFragmentCb = toReposFragmentCb

        return binding.root
    }
}