package com.possible.demo.features.repos

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.possible.demo.R
import com.possible.demo.databinding.FragmentReposBinding

class ReposFragment(private val login: String) : androidx.fragment.app.Fragment() {

    private lateinit var reposViewModel: ReposViewModel

    lateinit var toReposFragmentCb: (login: String) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentReposBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_repos, container, false)

        reposViewModel =
                ViewModelProviders.of(
                        this).get(ReposViewModel::class.java)
        reposViewModel.login = login
        lifecycle.addObserver(reposViewModel)

        binding.reposViewModel = reposViewModel

        val adapter = ReposAdapter()
        binding.reposList.adapter = adapter

        reposViewModel.repos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.toList())
            }
        })

        binding.setLifecycleOwner(this)

        setUpReposFragmentCallback()

        return binding.root
    }

    private fun setUpReposFragmentCallback() {
        reposViewModel.toReposFragmentCb = {
            toReposFragmentCb(it)
        }
    }
}