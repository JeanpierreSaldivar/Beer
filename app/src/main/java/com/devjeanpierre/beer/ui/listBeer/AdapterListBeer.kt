package com.devjeanpierre.beer.ui.listBeer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devjeanpierre.beer.BeerApp
import com.devjeanpierre.beer.R
import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.databinding.LsvItemBeerBinding
import com.devjeanpierre.beer.utils.loadByResourceWithoutCache

class AdapterListBeer(private val listener: AdapterListBeerListener): RecyclerView.Adapter<AdapterListBeer.MainViewHolder>() {
    private var dataList = mutableListOf<Beer>()
    fun setListData(data:List<Beer>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(BeerApp.appContext).inflate(R.layout.lsv_item_beer,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val beer = dataList[position]
        holder.bind(beer,listener)
    }

    override fun getItemCount(): Int =dataList.size

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LsvItemBeerBinding.bind(itemView)
        fun bind(beer:Beer,listener:AdapterListBeerListener)=with(itemView){
            binding.apply {
                title.text = beer.name
                description.text = beer.description
                image.loadByResourceWithoutCache(beer.image_url)
            }
        }
    }

}
interface AdapterListBeerListener {
    fun onClick(){}
}