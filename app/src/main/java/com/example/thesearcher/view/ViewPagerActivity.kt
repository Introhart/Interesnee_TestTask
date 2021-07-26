package com.example.thesearcher.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.thesearcher.DI.Injection
import com.example.thesearcher.R
import com.example.thesearcher.model.ImageInfo
import com.example.thesearcher.model.ViewModelKeeper
import com.example.thesearcher.view_model.MainActivityViewModel

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        Log.d("dbg", intent.getIntExtra("ARG", 0).toString())

        setupViewModel() // TODO :: CHECK
        setupUI();
    }

    private fun setupUI()
    {
//        pagerAdapter = ViewPagerAdapter(this, mutableListOf()) // TODO :: HANDLE NULL EXCEPTION
        pagerAdapter = ViewPagerAdapter( viewModel.images.value!!)// TODO :: HANDLE NULL EXCEPTION

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = pagerAdapter
        Log.d("dbg", "setupUI")
        viewPager.currentItem = intent.getIntExtra("ARG", 0)

    }

    private fun setupViewModel()
    {
        viewModel = ViewModelKeeper.viewModel
        viewModel.images.observe(this, renderImages)
    }

    private val renderImages = Observer<MutableList<ImageInfo>>{
//        pagerAdapter.update(it) // TODO :: REMEMBER
    }
    
}