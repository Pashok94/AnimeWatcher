package kpd.animewatcher.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kpd.animewatcher.com.R
import kpd.animewatcher.com.databinding.ItemMainBinding
import kpd.animewatcher.viewModel.models.AnimeVM

class MainAdapter(private val startVideoCallback: (String) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val animeList = arrayListOf<AnimeVM>()
    fun addNewAnime(anime: List<AnimeVM>) {
        val startPos = anime.size + 1
        animeList.addAll(anime)
        notifyItemRangeChanged(startPos, anime.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(animeList[position])
    }

    override fun getItemCount() = animeList.size

    inner class MainViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(anime: AnimeVM) {
            binding.animeTitle.text = anime.title
            setColors(anime)
            anime.trailerId?.let { trailerId ->
                binding.btnPlay.isVisible = true
                binding.btnPlay.setOnClickListener {
                    startVideoCallback(trailerId)
                }
            }
        }

        private fun setColors(anime: AnimeVM){
            binding.animePoster.load(anime.posterUrl){
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
        }
    }
}