package com.tapascodev.inspector.places.ui

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.databinding.ItemPlaceBinding
import com.tapascodev.inspector.places.domain.model.Place

class PlaceViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val binding = ItemPlaceBinding.bind(view)

    fun render (place: Place) {
        binding.apply {

            when(place.floor) {
                1 -> {
                    tvNumber.setTextColor(Color.WHITE)
                    tvCarPlate.setTextColor(Color.WHITE)
                    cardViewItem.setCardBackgroundColor(Color.BLUE)
                }
                2 -> cardViewItem.setCardBackgroundColor(Color.GREEN)
                3 -> cardViewItem.setCardBackgroundColor(Color.RED)
                4 -> cardViewItem.setCardBackgroundColor(Color.YELLOW)
            }

            tvNumber.text = place.number.toString()
        }
    }
}