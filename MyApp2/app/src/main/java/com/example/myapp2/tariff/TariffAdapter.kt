package com.example.myapp2.tariff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.R
import com.example.myapp2.Tariff

class TariffAdapter(private val tariffs: List<Tariff>) : RecyclerView.Adapter<TariffAdapter.TariffViewHolder>() {

    class TariffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: TextView = itemView.findViewById(R.id.textIdT)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textCost: TextView = itemView.findViewById(R.id.textCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TariffViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tariff, parent, false)
        return TariffViewHolder(view)
    }

    override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
        val tariff = tariffs[position]
        holder.textId.text = tariff.id.toString()
        holder.textDescription.text = tariff.description
        holder.textCost.text = tariff.cost.toString()
    }

    override fun getItemCount(): Int {
        return tariffs.size
    }
}