package my.assigment.neverbored.db

import androidx.lifecycle.LiveData
import androidx.room.*
import my.assigment.neverbored.models.TvShow

@Dao
interface ShowsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShows(tvShows: List<TvShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowToFavorites(tvShows: TvShow)

    @Delete
    suspend fun deleteTvShowFromFavorite(tvShow: TvShow)

    @Query("SELECT * FROM tv_shows_table")
    fun getAllTvShows(): List<TvShow>
}