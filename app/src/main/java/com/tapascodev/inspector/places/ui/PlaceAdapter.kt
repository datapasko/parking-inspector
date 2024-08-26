package com.tapascodev.inspector.places.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.R
import com.tapascodev.inspector.places.domain.model.Place

class PlaceAdapter(
    private var list: List<Place> = emptyList()
): RecyclerView.Adapter<PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.render(list[position])
    }
    fun updateList(newList: List<Place>){
        list = newList
        notifyDataSetChanged()
    }
}