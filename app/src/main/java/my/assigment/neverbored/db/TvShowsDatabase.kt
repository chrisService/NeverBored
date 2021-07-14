package my.assigment.neverbored.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import my.assigment.neverbored.models.TvShow

@Database(
    entities = [TvShow::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(SeasonConverter::class)
abstract class TvShowsDatabase : RoomDatabase(){

    abstract fun getShowsDao(): ShowsDao
}

