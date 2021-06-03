package com.example.movizapp.ui.main.home

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movizapp.R
import com.example.movizapp.base.BaseActivity
import com.example.movizapp.databinding.ActivityMainBinding
import com.example.movizapp.listeners.ActivityListener
import com.example.movizapp.ui.main.home.adapter.LoaderStateAdapter
import com.example.movizapp.ui.main.home.adapter.MoviesAdapter
import com.example.movizapp.ui.main.home.adapter.MoviesNoNetDataAdapter
import com.example.movizapp.utils.isConnected
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(),ActivityListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter
    override val layoutId: Int
        get() = R.layout.activity_main
    override val setToolbar: Boolean
        get() = false
    override val hideStatusBar: Boolean
        get() = false

    override fun fragmentLaunch() {
    }

    override fun initData() {
        setTitle(getString(R.string.Movies))
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        moviesAdapter= MoviesAdapter(this){title,pathlink->
            Log.e("details added",title+"--"+pathlink)
            mainViewModel.insertData(this,title,pathlink)
            Log.e("inserted","successfully")
        }
        initRecyclerview()
        if(this.isConnected){
            lifecycleScope.launch {
                mainViewModel.moviesList.collectLatest { response->
                    binding.apply {
                        progressBar.isVisible=false
                        recyclerview.isVisible=true
                    }
                    moviesAdapter.submitData(response)
                }
            }
        }
        else{
            mainViewModel.getMovieDetails(this)!!.observe(this, Observer {
                binding.progressBar.isVisible=false
                if (it == null) {
                  Log.e("database","Data Not Found")
                    Log.e("data null",Gson().toJson(it))
                }
                else {
                    recyclerviewNoNet.visibility= View.VISIBLE
                    Log.e("database","Data Found Successfully")
                    Log.e("data",Gson().toJson(it))
                    recyclerviewNoNet.layoutManager = GridLayoutManager(this,2,
                        GridLayoutManager.VERTICAL, false)
                    recyclerviewNoNet.adapter = MoviesNoNetDataAdapter(this,it)
                }
            })
        }
    }
    private fun initRecyclerview() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity,2)
                adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderStateAdapter { moviesAdapter::retry },
                    footer = LoaderStateAdapter { moviesAdapter::retry }
                )
            }
        }
    }
    override fun setupUI() {

    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }

    override fun onClicks() {

    }

    override fun getViewBinging(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun navigateToFragment(fragment: Fragment) {

    }

    override fun setTitle(title: String) {
       initializeToolbar(title)
    }
}