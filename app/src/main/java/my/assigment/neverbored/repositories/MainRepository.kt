package my.assigment.neverbored.repositories

import my.assigment.neverbored.Constansts
import my.assigment.neverbored.api.TmdbApiService
import my.assigment.neverbored.db.ShowsDao
import my.assigment.neverbored.models.TMDBResponse
import my.assigment.neverbored.models.TvShow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: TmdbApiService,
    private val dao: ShowsDao
){
    suspend fun getWeekTrending(): List<TvShow>{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constansts.API_KEY
        val tvShows = api.getWeekTrendingTvShows(queryParams).results!!
        try {
            dao.insertTvShows(tvShows)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return tvShows
    }


    suspend fun getShowsFromDB(): List<TvShow>{
        return dao.getAllTvShows()
    }

    suspend fun insertToFavorites(tvShow: TvShow){
        dao.insertTvShowToFavorites(tvShow)
    }

    suspend fun deleteFromFavorites(tvShow: TvShow){
        dao.deleteTvShowFromFavorite(tvShow)
    }


    suspend fun getDetails(id: String): TvShow{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constansts.API_KEY
        return api.getTvShowDetails(id, queryParams)
    }


    suspend fun getSimilar(id: String): TMDBResponse{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constansts.API_KEY
        return api.getSimilar(id, queryParams)
    }


    suspend fun getSearchResults(query: String): TMDBResponse{
        val queryParams = mutableMapOf<String, String>()
        queryParams["api_key"] = Constansts.API_KEY
        queryParams["query"] = query
        return api.getSearchResults(queryParams)
    }
}