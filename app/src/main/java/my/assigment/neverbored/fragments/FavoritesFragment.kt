package my.assigment.neverbored.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import m.otpremnica.android.utility.listeners.ViewModelListener
import my.assigment.neverbored.Constansts
import my.assigment.neverbored.R
import my.assigment.neverbored.adapters.TvShowsRVAdapret
import my.assigment.neverbored.adapters.interfaces.AdapterClickListener
import my.assigment.neverbored.databinding.FragmentFavoritesBinding
import my.assigment.neverbored.databinding.FragmentListBinding
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.viewModel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModel : MainViewModel

    private lateinit var bindings: FragmentFavoritesBinding
    lateinit var adapter: TvShowsRVAdapret

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindings = FragmentFavoritesBinding.inflate(layoutInflater)
        val view = bindings.root

        getRequest()

        return view
    }


    private fun getRequest(){

                GlobalScope.launch(Dispatchers.Main) {
                    val size  = withContext(Dispatchers.Default) { viewModel.getFromDb() }
                    setAdapter()
                    adapter.updateList(size.filter { it.favorite })
                }
    }

    private fun setAdapter(){

        adapter = TvShowsRVAdapret(mutableListOf(),object: AdapterClickListener<TvShow> {
            override fun onClick(response: TvShow?) {

                val outputString = Gson().toJson(response)
                val bundle = Bundle()
                bundle.putString(Constansts.TvShowID, outputString)
                findNavController().navigate(R.id.action_favoritesFragment_to_detailsFragment, bundle)
            }
        })
        bindings.rvTvShows.layoutManager = GridLayoutManager(requireContext(), 2)
        bindings.rvTvShows.adapter = adapter
    }

}