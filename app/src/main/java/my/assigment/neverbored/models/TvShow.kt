package my.assigment.neverbored.models



import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv_shows_table")
data class TvShow(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("seasons")
    val seasons: List<Season>?,

    var favorite: Boolean = false
)