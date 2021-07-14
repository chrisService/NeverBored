package my.assigment.neverbored.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import m.otpremnica.android.utility.listeners.ViewModelListener
import my.assigment.neverbored.Constansts
import my.assigment.neverbored.R
import my.assigment.neverbored.adapters.SimilarTvShowsRVAdapret
import my.assigment.neverbored.adapters.interfaces.AdapterClickListener
import my.assigment.neverbored.databinding.FragmentDetailsBinding
import my.assigment.neverbored.models.Season
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.viewModel.MainViewModel
import java.lang.Exception
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var bindings: FragmentDetailsBinding
    lateinit var adapter: SimilarTvShowsRVAdapret

    @Inject
    lateinit var viewModel: MainViewModel
    lateinit var id: String
    lateinit var details: TvShow
    var seasons = mutableListOf<Season>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentDetailsBinding.inflate(layoutInflater)
        val view = bindings.root

        setId()
        favoriteActions()

        return view
    }

    private fun setId() {
        details = Gson().fromJson(arguments?.getString(Constansts.TvShowID), TvShow::class.java)
        id = details.id.toString()
        setView()
        getSeasonsDetails()
        getSimilar()
    }


    private fun favoriteActions() {
        bindings.ivFavorite.setOnClickListener {

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    if (!details.favorite) {
                        details = viewModel.insertToFavorites(
                            TvShow(
                                details.id,
                                details.firstAirDate,
                                details.name,
                                details.originalName,
                                details.overview,
                                details.posterPath,
                                details.voteAverage,
                                details.seasons,
                                true
                            ), object : ViewModelListener {
                                override fun onStarted(message: String?) {
                                }

                                override fun onSuccess(message: String?) {
                                    bindings.ivFavorite.setImageResource(R.drawable.ic_favorite_black_40dp)
                                    Toast.makeText(
                                        requireContext(),
                                        "This TV show is added\nto your favorites list",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onFailure(message: String?) {
                                }
                            }
                        )
                    } else {
                        details = viewModel.deleteFromFavorites(
                            TvShow(
                                details.id,
                                details.firstAirDate,
                                details.name,
                                details.originalName,
                                details.overview,
                                details.posterPath,
                                details.voteAverage,
                                details.seasons,
                                false
                            ), object : ViewModelListener {
                                override fun onStarted(message: String?) {
                                }

                                override fun onSuccess(message: String?) {
                                    bindings.ivFavorite.setImageResource(R.drawable.ic_favorite_border_black_40dp)
                                    Toast.makeText(
                                        requireContext(),
                                        "This TV show is deleted\nfrom your favorites list",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onFailure(message: String?) {
                                }
                            }
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun getSeasonsDetails() {

        if (isInternetAvailable()){
            bindings.tvSimmilar.visibility = View.GONE

            viewModel.getDetails(id, object : ViewModelListener {
                override fun onStarted(message: String?) {
                    bindings.progresBar.visibility = View.VISIBLE
                }

                override fun onSuccess(message: String?) {
                    bindings.progresBar.visibility = View.GONE
                    seasons = viewModel.tvShowDetails.seasons!! as MutableList<Season>
                    setView()
                }

                override fun onFailure(message: String?) {
                    bindings.progresBar.visibility = View.GONE
                }
            })
        }else{

            bindings.tvSimmilar.visibility = View.VISIBLE
        }


    }

    private fun getSimilar() {

        viewModel.getSimilar(id, object : ViewModelListener {
            override fun onStarted(message: String?) {
            }

            override fun onSuccess(message: String?) {
                setAdapter()
                adapter.updateList(viewModel.similarTvShows)
            }

            override fun onFailure(message: String?) {
                bindings.progresBar.visibility = View.GONE
            }
        })
    }

    fun setView() {

        val poserUrl = "https://image.tmdb.org/t/p/w500" + details.posterPath

        bindings.tvRaiting.text = details.voteAverage.toString()
        if (details.favorite == true) {
            bindings.ivFavorite.setImageResource(R.drawable.ic_favorite_black_40dp)
        } else {
            bindings.ivFavorite.setImageResource(R.drawable.ic_favorite_border_black_40dp)
        }
        bindings.tvTitle.text = details.originalName
        Glide.with(bindings.ivPoster.context).load(poserUrl).into(bindings.ivPoster)
        bindings.tvDescription.text = details.overview
        if (!seasons.isEmpty()) {
            bindings.tvSeasons.text = appendSeasonsTitles(seasons)
        }

    }


    private fun appendSeasonsTitles(seasons: List<Season>): String {
        val sb = StringBuilder()
        for (season in seasons) {
            sb.append(season.name + " - " + season.episodeCount.toString() + " episodes" + "\n")
        }
        return sb.toString()
    }

    fun setAdapter() {
        adapter = SimilarTvShowsRVAdapret(mutableListOf(), object : AdapterClickListener<TvShow> {
            override fun onClick(response: TvShow?) {
                val outputString = Gson().toJson(response)
                val bundle = Bundle()
                bundle.putString(Constansts.TvShowID, outputString)
                findNavController().navigate(R.id.open_details, bundle)
            }
        })
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.HORIZONTAL
        bindings.rvSimmilar.layoutManager = llm
        bindings.rvSimmilar.adapter = adapter
    }


    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {

                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        else -> false
                    }
                }
            } else {
                connectivityManager.activeNetworkInfo.also { info ->
                    result = info != null && info.isConnected
                    return result
                }
            }
        }
        return result
    }
}
