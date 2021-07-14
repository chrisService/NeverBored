package my.assigment.neverbored.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.assigment.neverbored.models.Season

class SeasonConverter {

    @TypeConverter
    fun fromSeasonList(value: List<Season>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Season>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSeasonList(value: String): List<Season>? {
        val gson = Gson()
        val type = object : TypeToken<List<Season>>() {}.type
        return gson.fromJson(value, type)
    }
}