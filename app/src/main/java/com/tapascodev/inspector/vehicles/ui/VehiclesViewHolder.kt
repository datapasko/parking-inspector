package com.tapascodev.inspector.vehicles.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.databinding.ItemVehicleBinding
import com.tapascodev.inspector.vehicles.domain.model.Vehicle

class VehiclesViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val binding = ItemVehicleBinding.bind(view)

    fun render (vehicle: Vehicle, onItemClicked: (Vehicle) -> Unit, onDeleteItemClicked: (Int) -> Unit) {
        binding.apply {
            tvVehiclePlate.text = vehicle.plate
            tvVehicleModel.text = vehicle.model
            tvVehicleBrand.text = vehicle.brand
            tvVehicleName.text = vehicle.name

            ivPhone.setOnClickListener {
                shareCall(it.context, vehicle.phone)
            }

            ivEmail.setOnClickListener {
                sendEmail(it.context, vehicle.email)
            }

            itemView.setOnClickListener { onItemClicked(vehicle) }

            btnDelete.setOnClickListener { onDeleteItemClicked(adapterPosition) }
        }
    }

    private fun shareCall(context: Context, number: String) {
        val call = Uri.parse("tel:$number")
        val intent2 = Intent(Intent.ACTION_DIAL, call)
        context.startActivity(intent2)
    }

    private fun sendEmail(context: Context, email: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            type = "message/rfc822"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Send email")
        context.startActivity(shareIntent)
    }
}