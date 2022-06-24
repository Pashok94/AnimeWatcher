package kpd.animewatcher.viewModel.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kpd.animewatcher.model.models.animeModel.AnimeList
import kpd.animewatcher.model.repository.IRepository
import kpd.animewatcher.model.repository.remoteRepository.Result
import kpd.animewatcher.viewModel.models.AnimeVM

class MainViewModel(
    private val repository: IRepository
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeAnimeListResponse(repository.getAnimeList(1))
        }
    }

    private val animeLiveData = MutableLiveData<List<AnimeVM>>()
    fun observeAnimeLiveData(): LiveData<List<AnimeVM>> = animeLiveData

    fun loadNewPageAnime(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            observeAnimeListResponse(repository.getAnimeList(page))
        }
    }

    private fun observeAnimeListResponse(result: Result) {
        when (result) {
            is Result.Error -> {}
            is Result.Success<*> -> {
                animeLiveData.postValue((result.result as AnimeList).data.anime.map {
                    AnimeVM.fromModel(it)
                })
            }
        }
    }
}