package my.assigment.neverbored.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.plati.app.viewHolders.interfaces.ViewHolderClickListener
import my.assigment.neverbored.R
import my.assigment.neverbored.adapters.interfaces.AdapterClickListener
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.viewHolders.TvShowsViewHolder

class TvShowsRVAdapret(var tvShows: MutableList<TvShow>, val listener: AdapterClickListener<TvShow>): RecyclerView.Adapter<TvShowsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_tv_show_item, parent, false)
        return TvShowsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {

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