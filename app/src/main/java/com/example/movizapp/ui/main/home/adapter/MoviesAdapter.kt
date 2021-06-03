package com.example.movizapp.ui.main.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movizapp.databinding.MovieListItemBinding
import com.example.movizapp.model.now_playing.Result
import com.example.movizapp.ui.main.movie_details.MovieDetailsScrollingActivity
import com.example.movizapp.utils.loadImagesWithGlideExt
import kotlinx.android.synthetic.main.movie_list_item.view.*


class MoviesAdapter(private val context: Context,
                    private val functionInfo: (String, String) -> Unit) : PagingDataAdapter<Result, MoviesAdapter.MoviesViewHolder>(
    Diff()
) {


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val resultItem = getItem(position)
        if(resultItem!= null){
            holder.itemView.image.loadImagesWithGlideExt(
                "https://image.tmdb.org/t/p/w500"+resultItem.poster_path)
            holder.itemView.name.text=resultItem.title
        }
        holder.itemView.setOnClickListener {
            val intent=Intent(context, MovieDetailsScrollingActivity::class.java)
            intent.putExtra("movie_id",resultItem?.id.toString())
            intent.putExtra("movie_name",resultItem?.title.toString())
            context.startActivity(intent)
        }
        functionInfo.invoke(resultItem!!.title,"https://image.tmdb.org/t/p/w500"+resultItem.poster_path)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    class MoviesViewHolder(private val binding:MovieListItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    class Diff : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean  =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem == newItem
    }
}