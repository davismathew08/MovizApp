package com.example.movizapp.ui.main.movie_details

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movizapp.R
import com.example.movizapp.base.BaseActivity
import com.example.movizapp.databinding.ActivityMovieDetailsScrollingBinding
import com.example.movizapp.listeners.ActivityListener
import com.example.movizapp.model.movie_details.MovieDetailsResponse
import com.example.movizapp.utils.Status
import com.example.movizapp.utils.Toaster
import com.example.movizapp.utils.isConnected
import com.example.movizapp.utils.loadImagesWithGlideExt
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_details_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlin.math.abs

class MovieDetailsScrollingActivity : BaseActivity<ActivityMovieDetailsScrollingBinding>(),ActivityListener {

    private var passedMovieId=""
    private var passedMovieName=""
    private var geners=""
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    override val layoutId: Int
        get() = R.layout.activity_movie_details_scrolling
    override val setToolbar: Boolean
        get() = false
    override val hideStatusBar: Boolean
        get() = false

    override fun fragmentLaunch() {

    }

    override fun initData() {
        setSupportActionBar(toolbarMovieDetails)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_toolbar_back)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        passedMovieId=intent.getStringExtra("movie_id").toString()
        passedMovieName=intent.getStringExtra("movie_name").toString()
        Log.e("name and id", "$passedMovieId-$passedMovieName")
        if(passedMovieName.isNotBlank()){
            toolbarMovieDetails.title=passedMovieName
        }else{
            toolbarMovieDetails.title=getString(R.string.details)
        }
        app_bar_movie.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                //  State Expanded
                verticalOffset == 0 ->{
                    supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_toolbar_back)
                    toolbarMovieDetails.title=""

                }
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange ->{
                    supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_toolbar_back)
                    if(passedMovieName.isNotBlank()){
                        toolbarMovieDetails.title=passedMovieName
                    }else{
                        toolbarMovieDetails.title=getString(R.string.details)
                    }

                }
                else -> Log.e("onelse","toolbar")//  Do anything for Ideal State

            }
        })
        movieDetailsViewModel.fetchMovieDetails(passedMovieId)
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {
        movieDetailsViewModel= MovieDetailsViewModel()
    }

    override fun setupObserver() {
        movieDetailsViewModel.getMovieDetailsResponse().observe(this, androidx.lifecycle.Observer {
            when(it.status){
                Status.LOADING->showLoader()
                Status.SUCCESS->{
                    dismissLoader()
                    Log.e("responseproceeddetails", Gson().toJson(it))
                    if(it.data!=null){
                        geners=""
                        setUpDetails(it.data)
                    }else{
                        Toaster.showToast(this,getString(R.string.no_data),
                                Toaster.State.ERROR, Toast.LENGTH_LONG)
                    }
                }
                Status.ERROR->{
                    dismissLoader()
                    Toaster.showToast(this,getString(R.string.something_wrong),
                            Toaster.State.ERROR, Toast.LENGTH_LONG)
                }
                Status.NO_INTERNET->{
                    dismissLoader()
                    if(this.isConnected){
                        Toaster.showToast(this,getString(R.string.something_wrong),
                                Toaster.State.ERROR, Toast.LENGTH_LONG)
                    }else{
                        Toaster.showToast(this,getString(R.string.no_internet),
                                Toaster.State.ERROR, Toast.LENGTH_LONG)
                    }
                }

            }
        })
    }

    private fun setUpDetails(movieDetails: MovieDetailsResponse) {
        ivMovieBackGround.loadImagesWithGlideExt("https://image.tmdb.org/t/p/w500"+movieDetails.backdrop_path)
        tvMovieName.text=movieDetails.title
        tvMovieTag.text=movieDetails.tagline
        tvReleaseDateValue.text=movieDetails.release_date
        movieDetails.genres.forEach {
            geners = if(geners.isBlank()){ it.name }else{ geners+","+it.name }
        }
        tvGeneresValue.text=geners
        tvMovieDescription.text=movieDetails.overview
        ivPoster.loadImagesWithGlideExt("https://image.tmdb.org/t/p/w500"+movieDetails.poster_path)
    }

    override fun onClicks() {

    }

    override fun getViewBinging(): ActivityMovieDetailsScrollingBinding {
        return ActivityMovieDetailsScrollingBinding.inflate(layoutInflater)
    }

    override fun navigateToFragment(fragment: Fragment) {

    }

    override fun setTitle(title: String) {

    }


}