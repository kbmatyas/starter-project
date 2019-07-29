package com.possible.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import android.R




class MainActivity : AppCompatActivity() {

    var disposable: CompositeDisposable = CompositeDisposable()
    var adapter = GithubAdapter()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutManager = GridLayoutManager(this,3)

        val model = ViewModelProviders.of(this).get(GithubViewModel::class.java)

        setupViews()

        model.searchGithub(USERNAME_GITHUB, adapter)

        if (savedInstanceState == null) {
//            fragmentTransaction.add(R.layo)
        }

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
