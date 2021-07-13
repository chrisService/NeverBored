package my.assigment.neverbored.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import m.otpremnica.android.utility.listeners.ViewModelListener
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.repositories.MainRepository
import java.lang.Exception


class MainViewModel @ViewModelInject constructor(
   private val repository: MainRepository
) : ViewModel() {

    var tvShows = listOf<TvShow>()
    var similarTvShows = listOf<TvShow>()
    lateinit var tvShowDetails: TvShow


    fun getWeekTrending(viewModelListener: ViewModelListener){

        viewModelListener.onStarted()
        viewModelScope.launch {
            try {
                tvShows = repository.getWeekTrending().results!!
                viewModelListener.onSuccess()
            }catch (e: Exception){
                viewModelListener.onFailure(e.message)
            }
        }
    }

    fun getDetails(id: String, viewModelListener: ViewModelListener){

        viewModelListener.onStarted()
        viewModelScope.launch {
            try {
                tvShowDetails = repository.getDetails(id)!!
                viewModelListener.onSuccess()
            }catch (e: Exception){
                viewModelListener.onFailure(e.message)
            }
        }
    }

    fun getSimilar(id: String, viewModelListener: ViewModelListener){

        viewModelListener.onStarted()
        viewModelScope.launch {
            try {
                similarTvShows = repository.getSimilar(id).results!!
                viewModelListener.onSuccess()
            }catch (e: Exception){
                viewModelListener.onFailure(e.message)
            }
        }
    }


    fun getSearchResults(query: String, viewModelListener: ViewModelListener){

        viewModelListener.onStarted()
        viewModelScope.launch {
            try {
                tvShows = repository.getSearchResults(query).results!!
                viewModelListener.onSuccess()
            }catch (e: Exception){
                viewModelListener.onFailure(e.message)
            }
        }
    }


}