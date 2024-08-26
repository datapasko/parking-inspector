package com.tapascodev.inspector.lines.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.databinding.ItemLineBinding
import com.tapascodev.inspector.lines.domain.LineModel

class LineViewHolder (
    view: View
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemLineBinding.bind(view)

    fun render (lineModel: LineModel) {
        binding.apply {
            tvName.text = lineModel.name
            tvType.text = lineModel.type
            tvZone.text = lineModel.zone
        }
    }
}