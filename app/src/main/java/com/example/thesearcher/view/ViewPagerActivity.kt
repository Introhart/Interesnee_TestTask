package com.example.thesearcher.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.thesearcher.R
import com.example.thesearcher.appComponent
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

class ViewPagerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>
    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }

    private lateinit var viewPager: ViewPager2

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_view_pager)
        Log.d("dbg", intent.getIntExtra("ARG", 0).toString())
        setupViewModel()
        setupUI();
    }

    private fun setupUI()
    {
        viewPager = findViewById(R.id.viewPager)

        viewPager.adapter = adapter

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.images.collectLatest(adapter::submitData)
        }

    }

    private fun setupViewModel()
    {
//        viewModelProvider.get()
//        viewModel.setQuery("Apple")
    }



}