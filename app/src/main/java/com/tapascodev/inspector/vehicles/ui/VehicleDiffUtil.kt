package com.tapascodev.inspector.vehicles.ui

import androidx.recyclerview.widget.DiffUtil
import com.tapascodev.inspector.vehicles.domain.model.Vehicle

class VehicleDiffUtil(
    private val oldList: List<Vehicle>,
    private val newList: List<Vehicle>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}