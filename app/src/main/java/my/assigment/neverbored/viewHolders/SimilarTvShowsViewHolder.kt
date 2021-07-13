package my.assigment.neverbored.viewHolders

import android.view.TextureView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.plati.app.viewHolders.interfaces.ViewHolderClickListener
import my.assigment.neverbored.R
import my.assigment.neverbored.models.TvShow

class SimilarTvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(
        tvShow: TvShow,
        listener: ViewHolderClickListener<TvShow>
    ){
        val iv_poster = itemView.findViewById<ImageView>(R.id.iv_poster)
        val poserUrl = "https://image.tmdb.org/t/p/w500" + tvShow.posterPath
        Glide.with(iv_poster.context).load(poserUrl).into(iv_poster)

        itemView.setOnClickListener {
            listener.onClick(tvShow)
        }

    }


}