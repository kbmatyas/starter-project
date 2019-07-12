//package com.possible.demo
//
//import android.app.Activity
//import android.app.SearchManager
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//
//class SearchResultsActivity : Activity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search_results)
//        Log.v("onCreate:", "hey1")
//
//        handleIntent(intent)
//    }
//
//    /***SEARCHBAR HANDLING***/
//    override fun onNewIntent(intent: Intent) {
//        Log.v("newIntent:", "hey2")
//        handleIntent(intent)
//    }
//
//    private fun handleIntent(intent: Intent) {
//        if (Intent.ACTION_SEARCH == intent.action) {
//            val query = intent.getStringExtra(SearchManager.QUERY)
//            Log.v("query:", query)
//            //loadJSON(query)
//        }
//    }
//}
