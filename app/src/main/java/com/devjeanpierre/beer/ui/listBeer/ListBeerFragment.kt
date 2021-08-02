package com.devjeanpierre.beer.ui.listBeer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devjeanpierre.beer.BeerApp
import com.devjeanpierre.beer.R
import com.devjeanpierre.beer.databinding.FragmentListBeerBinding
import com.devjeanpierre.beer.presentation.BeerViewModel
import com.devjeanpierre.beer.core.Result
import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.ui.detailBeer.DetailBeerFragment
import com.devjeanpierre.beer.utils.hideKeyboard
import com.devjeanpierre.beer.utils.isInternetAvailable
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListBeerFragment : Fragment(){
    private val nameClass = this::class.java.name
    private var _binding: FragmentListBeerBinding? = null
    private val binding get() = _binding!!
    private val beerViewModel: BeerViewModel by viewModels()
    private lateinit var adapter: AdapterListBeer
    private lateinit var recycler: RecyclerView
    private var listBeer: List<Beer> ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBeerBinding.inflate(inflater, container, false)
        adapter= AdapterListBeer(object:AdapterListBeerListener{
            override fun onClick(beer: Beer, position: Int) {
                super.onClick(beer, position)
                requireActivity().hideKeyboard()
                dataForDeatilBeerFragment(beer)
                goToDeatilBeerFragment(beer, position)
            }
        })
        recyclerReady()
        getListBeer()
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                requireActivity().hideKeyboard()
                getListForName()
            }
            false
        }
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if(it.isEmpty()) getListBeer() else getListForName()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.ivClose.setOnClickListener {
            binding.etSearch.setText("")
            binding.emptyList.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
            requireActivity().hideKeyboard()
            getListBeer()

        }
        return binding.root
    }

    private fun recyclerReady() {
        binding.recycler.let {
            recycler = it
        }
        recycler.layoutManager = LinearLayoutManager(BeerApp.appContext)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
    }

    private fun dataForDeatilBeerFragment(beer: Beer) {
        val beerJson = Gson().toJson(beer)
        setFragmentResult("requestKey",bundleOf("beerData" to beerJson))
    }

    private fun goToDeatilBeerFragment(beer: Beer, position: Int) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container_fragment, DetailBeerFragment())
            addToBackStack(nameClass)
        }
    }

    private fun getListForName() {
        if (isInternetAvailable(requireContext())) {
            if(listBeer.isNullOrEmpty()) getListBeer()
            val name = binding.etSearch.text.toString().lowercase(Locale.getDefault())
            val listFilter =
                listBeer?.filter { it.name.lowercase(Locale.getDefault()).contains(name) }
            if (listFilter.isNullOrEmpty()) {
                noResultList()
            } else {
                binding.recycler.visibility = View.VISIBLE
                binding.emptyList.visibility = View.GONE
                listFilter.let { adapter.setListData(it) }
                binding.recycler.visibility = View.VISIBLE
            }
        }else{
            noConnection()
        }
    }

    private fun noResultList() {
        binding.imageEmpty.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_no_beer))
        errorOrEmptyShow(BeerApp.appContext.resources.getText(R.string.empty_message) as String,true)
    }

    private fun noConnection() {
        binding.imageEmpty.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_baseline_wifi_off_24))
        errorOrEmptyShow(BeerApp.appContext.resources.getText(R.string.no_connection) as String,true)
    }


    private fun getListBeer() {
        if (isInternetAvailable(requireContext())){
            beerViewModel.getListBeer().observe(viewLifecycleOwner,{result->
                when(result){
                    is Result.Loading ->{
                        binding.recycler.visibility = View.INVISIBLE
                        binding.shimmer.apply{
                            visibility = View.VISIBLE
                            startShimmer()
                        }
                    }
                    is Result.Success ->{
                        binding.shimmer.apply{
                            stopShimmer()
                            visibility = View.GONE
                        }
                        listBeer = result.data
                        listBeer?.let {
                            checkEmptyList(it)
                        }


                    }
                    is Result.Failure->{
                        errorOrEmptyShow(result.exception.message ?: "An unexpected error occurred",true)
                    }
                }
            })
        }else{
            noConnection()
        }
    }

    private fun checkEmptyList(list: List<Beer>) {
        if (list.isNullOrEmpty()){
            noResultList()
        }else{
            listShow(list)
            binding.recycler.visibility = View.VISIBLE
        }
    }

    private fun listShow(listBeer: List<Beer>) {
        adapter.setListData(listBeer)
    }

    private fun errorOrEmptyShow(message:String, iconVisibility:Boolean){
        binding.shimmer.apply{
            stopShimmer()
            visibility = View.GONE
        }
        binding.recycler.visibility = View.GONE
        binding.emptyList.visibility = View.VISIBLE
        binding.imageEmpty.visibility = if(iconVisibility)View.VISIBLE else View.GONE
        binding.messageEmpty.text = message
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}