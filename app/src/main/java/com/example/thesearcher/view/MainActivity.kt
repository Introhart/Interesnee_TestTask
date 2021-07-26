package com.example.thesearcher

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesearcher.DI.Injection
import com.example.thesearcher.model.ImageInfo
import com.example.thesearcher.model.ViewModelKeeper
import com.example.thesearcher.view.RecyclerViewAdapter
import com.example.thesearcher.view_model.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(MainActivityViewModel::class.java)

        ViewModelKeeper.viewModel = viewModel

        viewModel.getImagesOnRequest("banana") // TODO :: REMOVE ME
        viewModel.images.observe(this, renderImages)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView?.setIconifiedByDefault(false)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupUI(){
        recyclerView = findViewById(R.id.recyclerView)

        GridLayoutManager(
            this,
            3,
            RecyclerView.VERTICAL,
            false
        ).apply {
            recyclerView.layoutManager = this
        }

        adapter = RecyclerViewAdapter(mutableListOf()) //TODO :: WHAT ??
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(onScrollListener)
    }

    //TODO :: HANDLE ERRORS IN MODEL!!
    private val onScrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            // DOWN
            if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                Log.d("dbg", "DOWN")
                viewModel.getNextPage()
            }

            // UP
            if (!recyclerView.canScrollVertically(-1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                Log.d("dbg", "UP")
                viewModel.getPrevPage()
            }
        }

    }

    private val renderImages = Observer<MutableList<ImageInfo>> {
        Log.d("dbg", "Image list updated")
        adapter.update(it)

    }

}