package com.example.thesearcher.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.addRepeatingJob
import androidx.viewpager2.widget.ViewPager2
import com.example.thesearcher.R
import com.example.thesearcher.data.Network.Model.ImagesResult
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

class ViewPagerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>
    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }

//    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ViewPagerAdapter(this) // TODO :: WHY ???
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_pager)
        Log.d("dbg", intent.getIntExtra("ARG", 0).toString())

        setupViewModel() // TODO :: CHECK
        setupUI();
    }

    private fun setupUI()
    {
//        pagerAdapter = ViewPagerAdapter( viewModel.images)// TODO :: HANDLE NULL EXCEPTION
        viewPager = findViewById(R.id.viewPager)

        viewPager.adapter = adapter
        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.images.collectLatest(adapter::submitData)
        }
//        viewPager.currentItem = intent.getIntExtra("ARG", 0)

    }

    private fun setupViewModel()
    {
        viewModelProvider.get()
//        viewModel = ViewModelKeeper.viewModel
//        viewModel.images.observe(this, renderImages)
    }

}