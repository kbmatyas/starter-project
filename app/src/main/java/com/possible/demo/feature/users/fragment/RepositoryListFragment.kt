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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.R
import com.possible.demo.RepositoryListBinding
import com.possible.demo.data.remote.repository.GitHubRepoRepository
import com.possible.demo.feature.users.adapters.RepositoryAdapter
import com.possible.demo.feature.users.viewmodel.RepositoryListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RepositoryListFragment : DaggerFragment() {

    private val args: RepositoryListFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: RepositoryListViewModelFactory

    private lateinit var binding: RepositoryListBinding
    private lateinit var viewModel: RepositoryListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        DataBindingUtil.inflate<RepositoryListBinding>(inflater, R.layout.fragment_repository_list, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = getString(R.string.repositories_for, args.username)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        viewModel =
            ViewModelProviders.of(this, viewModelFactory.setup(args.username)).get(RepositoryListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = RepositoryAdapter()
        binding.repoList.adapter = adapter
        binding.repoList.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        viewModel.repositories.observe(viewLifecycleOwner, Observer(adapter::submitList))
    }

    class RepositoryListViewModelFactory @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) : ViewModelProvider.Factory {

        private var userName: String? = null

        fun setup(userName: String): RepositoryListViewModelFactory = this.apply {
            this@RepositoryListViewModelFactory.userName = userName
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = RepositoryListViewModel(userName.orEmpty(), gitHubRepoRepository) as T
    }
}