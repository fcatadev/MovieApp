package com.fcadev.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.movieapp.databinding.NowPlayingListRowBinding
import com.fcadev.movieapp.model.nowplayingmovies.NowPlayingResult
import com.fcadev.movieapp.model.topratedtvshows.TopRatedTvShowsResult

class NowPlayingMovieAdapter(private val nowPlayingList : ArrayList<NowPlayingResult>) : RecyclerView.Adapter<NowPlayingMovieAdapter.NowPlayerMovieViewHolder>() {

    private val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    var onItemClick : ((NowPlayingResult) -> Unit)? = null

    class NowPlayerMovieViewHolder(var binding: NowPlayingListRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayerMovieViewHolder {
        val binding = NowPlayingListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NowPlayerMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayerMovieViewHolder, position: Int) {
        val imageUrl = BASE_IMG_URL + nowPlayingList[position].poster_path
        Glide.with(holder.binding.root.context).load(imageUrl)
            .centerCrop()
            .into(holder.binding.nowPlayingMovieImage)

        holder.binding.nowPlayingMovieName.text = nowPlayingList[position].original_title
        holder.binding.nowPlayingMovieDate.text = nowPlayingList[position].release_date
        holder.binding.nowPlayingMovieRateText.text = nowPlayingList[position].vote_average.toString()

        holder.binding.nowPlayingMovieCardLayout.setOnClickListener {
            onItemClick?.invoke(nowPlayingList[position])
        }

    }

    override fun getItemCount(): Int {
        return nowPlayingList.size
    }

    fun updateNowPlayingMovieList(newNowPlayingMovieList: MutableList<NowPlayingResult>){
        nowPlayingList.clear()
        nowPlayingList.addAll(newNowPlayingMovieList)
        notifyDataSetChanged()
    }

}