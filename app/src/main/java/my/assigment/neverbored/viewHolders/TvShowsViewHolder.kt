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

class TvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(
        tvShow: TvShow,
        listener: ViewHolderClickListener<TvShow>
    ){

        val tv_raiting = itemView.findViewById<TextView>(R.id.tv_raiting)
        val iv_favorite = itemView.findViewById<ImageView>(R.id.iv_favorite)
        val iv_poster = itemView.findViewById<ImageView>(R.id.iv_poster)
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)


        val poserUrl = "https://image.tmdb.org/t/p/w500" + tvShow.posterPath

        tv_raiting.text = tvShow.voteAverage.toString()
        if (tvShow.favorite){
            iv_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)
        }else{
            iv_favorite.setImageResource(R.drawable.ic_favorite_border_black_40dp)
        }
        Glide.with(iv_poster.context).load(poserUrl).into(iv_poster)
        tv_title.text = tvShow.originalName.toString()

        itemView.setOnClickListener {
            listener.onClick(tvShow)
        }

    }


}