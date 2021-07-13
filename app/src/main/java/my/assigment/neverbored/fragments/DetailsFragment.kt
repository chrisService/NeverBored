package my.assigment.neverbored.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import m.otpremnica.android.utility.listeners.ViewModelListener
import my.assigment.neverbored.R
import my.assigment.neverbored.adapters.SimilarTvShowsRVAdapret
import my.assigment.neverbored.adapters.interfaces.AdapterClickListener
import my.assigment.neverbored.databinding.FragmentDetailsBinding
import my.assigment.neverbored.models.Season
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.viewModel.MainViewModel
import java.lang.StringBuilder

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var bindings: FragmentDetailsBinding
    lateinit var adapter: SimilarTvShowsRVAdapret

    val viewModel : MainViewModel by viewModels()
    lateinit var id: String
    lateinit var details: TvShow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentDetailsBinding.inflate(layoutInflater)
        val view = bindings.root

        setId()

        return view
    }

    private fun setId(){
        id = arguments?.getInt("tvShowId").toString()
        getDetails()
        getSimilar()
    }


    private fun getDetails(){

        viewModel.getDetails(id, object : ViewModelListener {
            override fun onStarted(message: String?) {
                bindings.progresBar.visibility = View.VISIBLE
            }
            override fun onSuccess(message: String?) {
                bindings.progresBar.visibility = View.GONE
                details = viewModel.tvShowDetails
                setView()
            }
            override fun onFailure(message: String?) {
            }
        })
    }

    private fun getSimilar(){

        viewModel.getSimilar(id, object : ViewModelListener {
            override fun onStarted(message: String?) {
            }
            override fun onSuccess(message: String?) {
                setAdapter()
                adapter.updateList(viewModel.similarTvShows)
            }
            override fun onFailure(message: String?) {
            }
        })
    }

    fun setView(){

        val poserUrl = "https://image.tmdb.org/t/p/w500" + details.posterPath

        bindings.tvRaiting.text = details.voteAverage.toString()
        if (details.favorite){
            bindings.ivFavorite.setImageResource(R.drawable.ic_favorite_black_40dp)
        }else{
            bindings.ivFavorite.setImageResource(R.drawable.ic_favorite_border_black_40dp)
        }
        bindings.tvTitle.text = details.originalName
        Glide.with(bindings.ivPoster.context).load(poserUrl).into(bindings.ivPoster)
        bindings.tvDescription.text = details.overview
        bindings.tvSeasons.text = appendSeasonsTitles(details.seasons!!)

    }


    private fun appendSeasonsTitles(seasons: List<Season>): String{
        val sb = StringBuilder()
        for(season in seasons){
            sb.append(season.name + " - " + season.episodeCount.toString() + " episodes" + "\n")
        }
        return sb.toString()
    }

    fun setAdapter(){
        adapter = SimilarTvShowsRVAdapret(mutableListOf(),object: AdapterClickListener<TvShow> {
            override fun onClick(response: TvShow?) {
                val bundle = Bundle()
                bundle.putInt("tvShowId", response?.id!!)
                findNavController().navigate(R.id.open_details, bundle)
            }
        })
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.HORIZONTAL
        bindings.rvSimmilar.layoutManager = llm
        bindings.rvSimmilar.adapter = adapter
    }

}