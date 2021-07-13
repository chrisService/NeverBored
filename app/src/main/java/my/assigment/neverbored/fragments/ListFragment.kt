package my.assigment.neverbored.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import m.otpremnica.android.utility.listeners.ViewModelListener
import my.assigment.neverbored.R
import my.assigment.neverbored.adapters.TvShowsRVAdapret
import my.assigment.neverbored.adapters.interfaces.AdapterClickListener
import my.assigment.neverbored.databinding.FragmentListBinding
import my.assigment.neverbored.models.TvShow
import my.assigment.neverbored.viewModel.MainViewModel


@AndroidEntryPoint
class ListFragment : Fragment() {


    private lateinit var bindings: FragmentListBinding
    lateinit var adapter: TvShowsRVAdapret
    lateinit var etSearch: EditText

    val viewModel : MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentListBinding.inflate(layoutInflater)
        val view = bindings.root

        etSearch = view.findViewById(R.id.et_search)

        getRequest()
        search()

        return view
    }


    private fun search(){
        etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (etSearch.text.toString().length > 2){
                    viewModel.getSearchResults(etSearch.text.toString(), object : ViewModelListener{
                        override fun onStarted(message: String?) {
                        }
                        override fun onSuccess(message: String?) {
                            setAdapter()
                            adapter.updateList(viewModel.tvShows)
                        }
                        override fun onFailure(message: String?) {
                        }
                    })
                }else if(etSearch.text.toString().isEmpty()){
                    getRequest()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun getRequest(){
            viewModel.getWeekTrending(object : ViewModelListener{
                override fun onStarted(message: String?) {
                    bindings.progresBar.visibility = View.VISIBLE
                }
                override fun onSuccess(message: String?) {
                    bindings.progresBar.visibility = View.GONE
                    setAdapter()
                    adapter.updateList(viewModel.tvShows)
                }
                override fun onFailure(message: String?) {
                }
            })
    }


    private fun setAdapter(){

        adapter = TvShowsRVAdapret(mutableListOf(),object: AdapterClickListener<TvShow>{
            override fun onClick(response: TvShow?) {
                val bundle = Bundle()
                bundle.putInt("tvShowId", response?.id!!)
                findNavController().navigate(R.id.action_listFragment_to_detailsFragment, bundle)
            }
        })
        bindings.rvTvShows.layoutManager = GridLayoutManager(requireContext(), 2)
        bindings.rvTvShows.adapter = adapter
    }

}