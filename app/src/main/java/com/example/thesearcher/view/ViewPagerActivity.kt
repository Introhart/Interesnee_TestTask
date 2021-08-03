package com.example.thesearcher.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.addRepeatingJob
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.thesearcher.R
import com.example.thesearcher.appComponent
import com.example.thesearcher.tempTestObject
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.wait
import javax.inject.Inject
import javax.inject.Provider

class ViewPagerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>
//    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }
    private val viewModel = tempTestObject.viewModel
    
    private lateinit var viewPager: ViewPager2
    private var originalPageUrl: String? = null

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ViewPagerAdapter(this)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_view_pager)
        setupViewModel()
        setupUI();


//        var itemNum: Int = intent.getIntExtra("ARG", 0)
//        Log.d("dbg", "Current item : ${itemNum.toString()}")
//        viewPager.setCurrentItem(itemNum, true)
//        viewPager.currentItem = itemNum
        viewPager.setCurrentItem(intent.getIntExtra("ARG", 0), false)
    }

    @ExperimentalCoroutinesApi
    private fun setupUI()
    {
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        addRepeatingJob(Lifecycle.State.STARTED) {
             viewModel.images.collectLatest(adapter::submitData)
        }

        // TODO :: Костыль? -> Да, костыль. Но работает.
        adapter.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onStateRestorationPolicyChanged() {
                    super.onStateRestorationPolicyChanged()
                    viewPager.setCurrentItem(intent.getIntExtra("ARG", 0), false)
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.view_pager_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_in_browser -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel()
    {
//        viewModelProvider.get()
//        viewModel.setQuery("Apple")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("dbg", "onDestroy")
    }
}