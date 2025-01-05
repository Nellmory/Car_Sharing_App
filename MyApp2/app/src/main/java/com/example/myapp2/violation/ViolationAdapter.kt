package com.example.myapp2.violation

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.R
import java.text.SimpleDateFormat
import java.util.Locale

class ViolationAdapter(private val violations: List<com.example.myapp2.Violation>) : RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder>() {

    class ViolationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textIdV: TextView = itemView.findViewById(R.id.textIdV)
        val textDescV: TextView = itemView.findViewById(R.id.textDescV)
        val textDateV: TextView = itemView.findViewById(R.id.textDateV)
        val textFineV: TextView = itemView.findViewById(R.id.textFineV)
        val textClientIDV: TextView = itemView.findViewById(R.id.textClientIdV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViolationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_violation, parent, false)
        return ViolationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViolationViewHolder, position: Int) {
        val violation = violations[position]

        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val date = inputFormat.parse(violation.date)

        holder.textIdV.text = violation.id.toString()
        holder.textDescV.text = violation.description
        holder.textDateV.text = Editable.Factory.getInstance().newEditable(outputFormat.format(date))
        holder.textFineV.text = violation.fine.toString()
        holder.textClientIDV.text = violation.client_id.toString()
    }

    override fun getItemCount(): Int {
        return violations.size
    }
}