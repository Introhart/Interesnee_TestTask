package com.example.thesearcher

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesearcher.view.RecyclerViewAdapter
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>

    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }

    private lateinit var recyclerView : RecyclerView

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(this)
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel(){
        viewModelProvider.get()
//        viewModel.setQuery("apple")
    }
    lateinit var job: Job
    private fun setupUI(){
        recyclerView = findViewById(R.id.recyclerView)

        GridLayoutManager(this,3, RecyclerView.VERTICAL,false
        ).apply {
            recyclerView.layoutManager = this
        }

        recyclerView.adapter = adapter


        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.images.collectLatest(adapter::submitData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query ?: " ")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRestart() {
        super.onRestart()

    }
}

