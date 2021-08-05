package com.example.thesearcher

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesearcher.view.MainView.ImagesLoadStateAdapter
import com.example.thesearcher.view.RecyclerViewAdapter
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

// TODO :: Produce VM with fabric. (Ныне -> Костыль)
object TempTestObject{
    lateinit var viewModel: MainActivityViewModel
}

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>

    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }

    private lateinit var recyclerView : RecyclerView

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(this)
}

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel(){
        viewModelProvider.get()
        TempTestObject.viewModel = viewModel
    }

    @ExperimentalCoroutinesApi
    private fun setupUI(){
        recyclerView = findViewById(R.id.recyclerView)

        // TODO :: Set screen-size-based spanCount
        GridLayoutManager(this,3, RecyclerView.VERTICAL,false
        ).apply {
            recyclerView.layoutManager = this
        }

        // TODO :: Starts with too big delay (Apparently right after http result receiving)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ImagesLoadStateAdapter(),
            footer = ImagesLoadStateAdapter()
        )

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.images.collectLatest(adapter::submitData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                viewModel.setQuery(query ?: " ")
                if (connectivityManager.isDefaultNetworkActive){
                    viewModel.setQuery(query ?: " ")
                } else {
                    Toast.makeText(applicationContext, R.string.no_internet_connection, Toast.LENGTH_LONG).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}
