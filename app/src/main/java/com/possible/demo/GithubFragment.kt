package com.possible.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GithubFragment : Fragment() {
    private lateinit var viewModel: GithubViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.github_main_view, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object{

    }

    fun openCardPage() {

    }


//    override fun onCreate(){
//
//    }
//
//    override fun onPause(){
//
//    }


}

/*  var disposable: CompositeDisposable = CompositeDisposable()
    var adapter = GithubAdapter()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_main_view)

        layoutManager = LinearLayoutManager(this)

        val model = ViewModelProviders.of(this).get(GithubViewModel::class.java)

        setupViews()

        model.searchGithub(USERNAME_GITHUB, adapter)

    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.githubRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }



    override fun onDestroy() {
        super.onDestroy()
        disposable?.clear()
    }


    companion object {
//        const val BASE_URL_GITHUB = "https://api.github.com/"
        const val USERNAME_GITHUB = "talinomedina"
//        const val TAG_GITHUB = "GitHub"
    } */