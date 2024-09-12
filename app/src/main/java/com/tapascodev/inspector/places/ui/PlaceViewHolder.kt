package com.tapascodev.inspector.places.ui

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.R
import com.tapascodev.inspector.databinding.ItemPlaceBinding
import com.tapascodev.inspector.places.domain.model.Place

class PlaceViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val binding = ItemPlaceBinding.bind(view)

    fun render (place: Place) {
        binding.apply {

            when(place.floor) {
                1 -> cardViewItem.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.blue_20))
                2 -> cardViewItem.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green_20))
                3 -> cardViewItem.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red_20))
                4 -> cardViewItem.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.orange_20))
            }

            tvNumber.text = place.number.toString()
            tvCarPlate.text = place.currentRental?.vehicle ?: "Free"
        }
    }
}