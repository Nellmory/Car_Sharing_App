package com.example.myapp2.rentViolation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.R
import com.example.myapp2.RentViolation

class RentViolationAdapter(private val rentViolations: List<RentViolation>) : RecyclerView.Adapter<RentViolationAdapter.RentViolationViewHolder>() {

    class RentViolationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textIdR: TextView = itemView.findViewById(R.id.textIdR)
        val textIdV: TextView = itemView.findViewById(R.id.textIdV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViolationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rent_violation, parent, false)
        return RentViolationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RentViolationViewHolder, position: Int) {
        val rentViolation = rentViolations[position]

        holder.textIdR.text = rentViolation.rent_id.toString()
        holder.textIdV.text = rentViolation.violation_id.toString()
    }

    override fun getItemCount(): Int {
        return rentViolations.size
    }
}