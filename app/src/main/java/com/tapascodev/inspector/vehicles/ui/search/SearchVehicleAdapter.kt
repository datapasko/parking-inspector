package com.tapascodev.inspector.vehicles.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.R
import com.tapascodev.inspector.databinding.ItemVehicleBinding
import com.tapascodev.inspector.vehicles.domain.model.Vehicle

class SearchVehicleAdapter(
    private var list: List<Vehicle> = emptyList(),
    private val onItemClicked: (Vehicle) -> Unit
): RecyclerView.Adapter<SearchVehicleAdapter.VehiclesViewHolder>() {

    inner class VehiclesViewHolder(
        view: View,
        onItemClicked: (Int) -> Unit
    ): RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemVehicleBinding.bind(view)

        fun render (vehicle: Vehicle) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        return VehiclesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        ) {
            onItemClicked(list[it])
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {
        holder.render(list[position])
    }

    fun updateList(newList: List<Vehicle>) {
        list = newList
        notifyDataSetChanged()
    }
}