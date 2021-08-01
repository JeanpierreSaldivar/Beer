package com.devjeanpierre.beer.ui.listBeer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devjeanpierre.beer.BeerApp
import com.devjeanpierre.beer.R
import com.devjeanpierre.beer.databinding.FragmentListBeerBinding
import com.devjeanpierre.beer.presentation.BeerViewModel
import com.devjeanpierre.beer.core.Result
import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListBeerFragment : Fragment(){
    private var _binding: FragmentListBeerBinding? = null
    private val binding get() = _binding!!
    private val beerViewModel: BeerViewModel by viewModels()
    private lateinit var adapter: AdapterListBeer
    private lateinit var recycler: RecyclerView
    private lateinit var listBeer: List<Beer>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBeerBinding.inflate(inflater, container, false)
        adapter= AdapterListBeer(object:AdapterListBeerListener{
            override fun onClick() {
                super.onClick()

            }
        })
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

    private fun getListForName() {
        val  name = binding.etSearch.text.toString().lowercase(Locale.getDefault())
        val listFilter = listBeer.filter{it.name.lowercase(Locale.getDefault()).contains(name)}
        if (listFilter.isNullOrEmpty()){
            errorOrEmptyShow(BeerApp.appContext.resources.getText(R.string.empty_message) as String,false)
        }else{
            binding.recycler.visibility = View.VISIBLE
            binding.emptyList.visibility = View.GONE
            listFilter.let { adapter.setListData(it) }
            binding.recycler.visibility = View.VISIBLE
        }

    }


    private fun getListBeer() {
        beerViewModel.getListBeer().observe(viewLifecycleOwner, Observer {result->
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
                    listBeer?.let{
                        checkEmptyList(it)
                    }

                }
                is Result.Failure->{
                    errorOrEmptyShow(result.exception.message ?: "Ocurrió un error inesperado",true)
                }
            }
        })
    }

    private fun checkEmptyList(list: List<Beer>) {
        if (list.isNullOrEmpty()){
            errorOrEmptyShow(BeerApp.appContext.resources.getText(R.string.empty_message) as String,false)
        }else{
            listShow(list)
            binding.recycler.visibility = View.VISIBLE
        }
    }

    private fun listShow(listBeer: List<Beer>) {
        binding.recycler.let {
            recycler = it
        }
        recycler.layoutManager = LinearLayoutManager(BeerApp.appContext)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
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