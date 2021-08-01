package com.example.thesearcher

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesearcher.view.RecyclerViewAdapter
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>

    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }

    private lateinit var recyclerView : RecyclerView

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(this) // TODO :: WHY ???
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
        viewModel.setQuery("Apple")
    }

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
}

