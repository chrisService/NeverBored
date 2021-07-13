package my.assigment.neverbored.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.plati.app.viewHolders.interfaces.ViewHolderClickListener
import my.assigment.neverbored.R
import my.assigment.neverbored.adapters.interfaces.AdapterClickListener
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.viewHolders.SimilarTvShowsViewHolder

class SimilarTvShowsRVAdapret(var tvShows: MutableList<TvShow>, val listener: AdapterClickListener<TvShow>): RecyclerView.Adapter<SimilarTvShowsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarTvShowsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_similar_tv_show_item, parent, false)
        return SimilarTvShowsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarTvShowsViewHolder, position: Int) {

        val item = tvShows[position]

        holder.bind(item, object: ViewHolderClickListener<TvShow>{
            override fun onClick(response: TvShow?) {
                listener.onClick(response)
            }
        })
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    fun updateList(data: List<TvShow>){
        tvShows.clear()
        tvShows.addAll(data)
        notifyDataSetChanged()
    }

}