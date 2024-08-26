package com.tapascodev.inspector.lines.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.R
import com.tapascodev.inspector.lines.domain.LineModel

class LineAdapter (
    private var linesList: List<LineModel> = emptyList()
) : RecyclerView.Adapter<LineViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        return LineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_line, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        holder.render(linesList[position])
    }

    override fun getItemCount() = linesList.size


    fun updateList(list: List<LineModel>){
        linesList = list
        notifyDataSetChanged()
    }
}