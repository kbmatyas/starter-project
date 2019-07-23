package com.possible.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable


class MainActivity : AppCompatActivity() {

    var disposable: CompositeDisposable = CompositeDisposable()
    var adapter = GithubAdapter()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = GridLayoutManager(this,3)

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
    }
}
