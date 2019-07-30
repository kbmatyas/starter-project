package com.possible.demo

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragmentActivity : AppCompatActivity()  {

    @LayoutRes
    protected open fun getLayoutResId() = R.layout.base_fragment_activity

    protected abstract fun createFragment() : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("LEXIE").d("BaseFragmentActivity: onCreate()")
        setContentView(getLayoutResId())

        var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(fragment == null) {
            fragment = createFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("LEXIE").d("BaseFragmentActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("LEXIE").d("BaseFragmentActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("LEXIE").d("BaseFragmentActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("LEXIE").d("BaseFragmentActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("LEXIE").d("BaseFragmentActivity: onDestroy()")
    }

}