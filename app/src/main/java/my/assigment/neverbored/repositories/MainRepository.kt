package my.assigment.neverbored.repositories

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import my.assigment.neverbored.Constransts
import my.assigment.neverbored.api.TmdbApiService
import my.assigment.neverbored.models.TMDBResponse
import my.assigment.neverbored.models.TvShow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: TmdbApiService
){


    suspend fun getWeekTrending(): TMDBResponse{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constransts.API_KEY
        return api.getWeekTrendingTvShows(queryParams)
    }


    suspend fun getDetails(id: String): TvShow{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constransts.API_KEY
        return api.getTvShowDetails(id, queryParams)
    }


    suspend fun getSimilar(id: String): TMDBResponse{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constransts.API_KEY
        return api.getSimilar(id, queryParams)
    }


    suspend fun getSearchResults(query: String): TMDBResponse{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constransts.API_KEY
        queryParams["query"] = query
        return api.getSearchResults(queryParams)
    }











}