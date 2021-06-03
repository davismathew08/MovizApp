package com.example.movizapp.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movizapp.R
import com.example.movizapp.model.MovieTableModel
import kotlinx.android.synthetic.main.list_item_movies_when_no_net.view.*


class MoviesNoNetDataAdapter(private val context: Context, private var movieList: List<MovieTableModel>)
    : RecyclerView.Adapter<MoviesNoNetDataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_movies_when_no_net, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.itemView.tvMovieName.text =movieList[position].name

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}