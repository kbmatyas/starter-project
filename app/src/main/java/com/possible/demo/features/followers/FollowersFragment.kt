package com.possible.demo.features.followers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.possible.demo.R
import com.possible.demo.databinding.FragmentMainBinding

class FollowersFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false)

        val followersViewModel =
                ViewModelProviders.of(
                        this).get(FollowersViewModel::class.java)
        lifecycle.addObserver(followersViewModel)

        binding.followersViewModel = followersViewModel

        val adapter = FollowersAdapter()
        binding.followersList.adapter = adapter

        followersViewModel.followers.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }
}