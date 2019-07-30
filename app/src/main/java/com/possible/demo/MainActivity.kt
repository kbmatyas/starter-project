package com.possible.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.possible.demo.GithubFollowerData.GithubAdapter
import com.possible.demo.GithubFollowerData.GithubFragment
import com.possible.demo.GithubFollowerData.GithubViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class MainActivity : BaseFragmentActivity(), GithubFragment.Callbacks {

    override fun createFragment(): Fragment {
        Timber.tag("LEXIE").d("MainActivity: createFragment()")
        return GithubFragment()
    }

    override fun onFollowerSelected(followerUsername : String) {
        Timber.tag("LEXIE").d("MainActivity: onFollowerSelected()")
//        if()
    }

    var disposable: CompositeDisposable = CompositeDisposable()
    var adapter = GithubAdapter()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    val fragmentManager = supportFragmentManager
    val fragmentTransaction = supportFragmentManager.beginTransaction()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = GridLayoutManager(this,2)

        val model = ViewModelProviders.of(this).get(GithubViewModel::class.java)

        setupViews()

        model.searchGithub(USERNAME_GITHUB, adapter)

        if (savedInstanceState == null) {
//            fragmentTransaction.add(R.layout.activity_main, )
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



