package com.devjeanpierre.beer.ui.detailBeer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import com.devjeanpierre.beer.R
import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.databinding.FragmentDetailBeerBinding
import com.devjeanpierre.beer.ui.listBeer.ListBeerFragment
import com.devjeanpierre.beer.utils.loadByResourceWithoutCache
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBeerFragment : Fragment() {
    private var _binding: FragmentDetailBeerBinding? = null
    private val binding get() = _binding!!
    private lateinit var beerDataJson: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBeerBinding.inflate(inflater, container, false)
        setFragmentResultListener("requestKey"){ _, bundle->
            bundle.getString("beerData")?.let{
                beerDataJson = it
                val beerData = Gson().fromJson(beerDataJson,Beer::class.java)
                binding.apply {
                    imageBeer.loadByResourceWithoutCache(beerData.image_url)
                    nameBeer.text = beerData.name
                    phBeer.text = beerData.ph.toString()
                    descriptionBeer.text = beerData.description
                }
            }
        }

        binding.backArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}