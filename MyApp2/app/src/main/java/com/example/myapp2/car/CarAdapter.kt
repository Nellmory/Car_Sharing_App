package com.example.myapp2.car

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.Car
import com.example.myapp2.R

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: TextView = itemView.findViewById(R.id.textIdC)
        val textVinNum: TextView = itemView.findViewById(R.id.textVinNum)
        val textLicensePlate: TextView = itemView.findViewById(R.id.textLicensePlate)
        val textModelId: TextView = itemView.findViewById(R.id.textModelId)
        val textColour: TextView = itemView.findViewById(R.id.textColour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.textId.text = car.id.toString()
        holder.textVinNum.text = car.vin_num
        holder.textLicensePlate.text = car.license_plate
        holder.textModelId.text = car.model_id.toString()
        holder.textColour.text = car.colour
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}