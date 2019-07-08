package com.possible.demo

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null

    private var followersArrayList: ArrayList<Follower>? = null
    private var adapter: DataAdapter? = null

    private var progressSpinner: ProgressBar? = null

    private val BASE_URL = "https://api.github.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()

        loadRecyclerView()

        progressSpinner = findViewById<ProgressBar>(R.id.progress_spinner)

        //TEST
        //loadJSON("JakeWaldner")
    }

    /***SEARCHBAR SETUP***/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            //requestFocus()
        }

        progressSpinner?.visibility = View.GONE

//        val searchItem: MenuItem = menu.findItem(R.id.action_search)
//        searchItem.expandActionView()
//        searchItem.setOnActionExpandListener(searchItem as MenuItemCompat.OnActionExpandListener, MenuItemCompat.OnActionExpandListener() {
//
//        }

        //Focuses search onCreate
//        val searchItem = menu?.findItem(R.id.action_search)
//        searchItem?.expandActionView()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.v("onPrepareOptionsMenu", "prepare")
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setQuery("", false)
            clearFocus()
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
        }

        return super.onPrepareOptionsMenu(menu)
    }

    //***not relevant to my search implementation, ignore
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        val id = item?.itemId
//
//        return if (id == R.id.search_button) {
//            val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//            searchManager.setOnDismissListener {
//                followers_list.visibility = View.INVISIBLE
//            }
//            onSearchRequested()
//        } else {
//            super.onOptionsItemSelected(item)
//        }
//    }

    private fun loadRecyclerView() {
        followers_list.setHasFixedSize(true)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        followers_list.layoutManager = layoutManager
    }

    /***SEARCHBAR QUERY HANDLING***/
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.v("newIntent:", "hey")
        progressSpinner?.visibility = View.VISIBLE
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.v("query:", query)
            loadJSON(query)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        followers_list.visibility = View.INVISIBLE
    }

    /***QUERY JSON HANDLING***/
    private fun loadJSON(username: String) {
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build()
        val gitHubRestApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubRestApi::class.java)

        Log.v("Username:", username)
        Log.v("URL:", gitHubRestApi.getFollowersList(username).toString())

        compositeDisposable?.add(gitHubRestApi.getFollowersList(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))
    }

    private fun handleResponse(followersList: List<Follower>) {

        Log.v("handleResponse", followersList.toString())

        if (followersList.isNotEmpty()) {
            //hide the no followers message
            no_followers_text.visibility = View.GONE
            progressSpinner?.visibility = View.GONE
            followers_list.visibility = View.VISIBLE

            followersArrayList = ArrayList(followersList)
            adapter = DataAdapter(followersArrayList!!)

            followers_list.adapter = adapter
        } else {
            /***Displays No Followers Message***/
            Log.v("emptyFollowersList", followersList.isEmpty().toString())
            progressSpinner?.visibility = View.GONE
            no_followers_text.visibility = View.VISIBLE

            //hide results in case previous results existed
            followers_list.visibility = View.INVISIBLE
        }
    }

    private fun handleError(error: Throwable) {
        Log.d("NotFound", error.localizedMessage)
        Log.e("Not Found", "", error)
        progressSpinner?.visibility = View.GONE
        followers_list.visibility = View.INVISIBLE
        Toast.makeText(this, "User not found", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}
