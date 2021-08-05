package com.example.thesearcher.view.PagerView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.thesearcher.R
import com.example.thesearcher.TempTestObject
import com.example.thesearcher.appComponent
import com.example.thesearcher.view.INTENT_EXTRA_ITEM_NUM
import com.example.thesearcher.view.ViewPagerAdapter
import com.example.thesearcher.view_model.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

class ViewPagerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<MainActivityViewModel.Factory>

//    private val viewModel: MainActivityViewModel by viewModels { viewModelProvider.get() }

    private val viewModel = TempTestObject.viewModel // Produce with fabric
    
    private lateinit var viewPager: ViewPager2

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ViewPagerAdapter(this)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_view_pager)

        setupUI();
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
                    viewPager.setCurrentItem(intent.getIntExtra(INTENT_EXTRA_ITEM_NUM, 0), false)
                }
            }
        )
    }

}