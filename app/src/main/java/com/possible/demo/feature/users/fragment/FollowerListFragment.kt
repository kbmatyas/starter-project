package com.possible.demo.feature.users.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.FollowerListFragmentBinding
import com.possible.demo.R
import com.possible.demo.data.remote.repository.FollowerRepository
import com.possible.demo.feature.users.adapters.UserAdapter
import com.possible.demo.feature.users.viewmodel.FollowerListViewModel
import com.possible.demo.shared.hideKeyboard
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FollowerListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactoryFollower: FollowerListViewModelFactory

    private lateinit var binding: FollowerListFragmentBinding
    private lateinit var viewModel: FollowerListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        DataBindingUtil.inflate<FollowerListFragmentBinding>(inflater, R.layout.fragment_follower_list, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactoryFollower).get(FollowerListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = UserAdapter()
        adapter.clickListener = { position ->
            val user = adapter.getItemOn(position)
            if (user != null) {
                findNavController().navigate(FollowerListFragmentDirections.actionFollowerListFragmentToRepositoryListFragment(user.login))
            }
        }
        binding.userList.adapter = adapter
        binding.userList.layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.item_per_row))

        binding.userList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    binding.userList.hideKeyboard()
                }
            }
        })

        viewModel.users.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    class FollowerListViewModelFactory @Inject constructor(private val followerRepository: FollowerRepository) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = FollowerListViewModel(followerRepository) as T
    }
}