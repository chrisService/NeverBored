package my.assigment.neverbored.api

import my.assigment.neverbored.models.TMDBResponse
import my.assigment.neverbored.models.TvShow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TmdbApiService {

    @GET("trending/tv/week")
    suspend fun getWeekTrendingTvShows(
        @QueryMap queryParams: Map<String, String>
    ): TMDBResponse

    @GET("tv/{id}")
    suspend fun getTvShowDetails(
        @Path ("id", encoded = true) id: String,
        @QueryMap queryParams: Map<String, String>
    ): TvShow

    @GET("tv/{id}/similar")
    suspend fun getSimilar(
        @Path ("id", encoded = true) id: String,
        @QueryMap queryParams: Map<String, String>
    ): TMDBResponse

    @GET("search/tv")
    suspend fun getSearchResults(
        @QueryMap queryParams: Map<String, String>
    ): TMDBResponse
}