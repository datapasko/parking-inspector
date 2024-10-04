package com.tapascodev.inspector.vehicles.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.R
import com.tapascodev.inspector.vehicles.domain.model.Vehicle

class VehiclesAdapter(
    private var list: List<Vehicle> = emptyList(),
    private val onItemClicked: (Vehicle) -> Unit,
    private val onDeleteItemClicked: (Int) -> Unit,
): RecyclerView.Adapter<VehiclesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        return VehiclesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {
        val vehicle = list[position]
        holder.render(vehicle, onItemClicked, onDeleteItemClicked)
    }

    fun updateList(newList: List<Vehicle>) {
        val vehicleDiffUtil = VehicleDiffUtil(list, newList)
        val result = DiffUtil.calculateDiff(vehicleDiffUtil)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int) {
        removeItem(position)
    }
}