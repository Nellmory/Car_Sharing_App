package com.example.myapp2.brand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.Brand
import com.example.myapp2.R

class BrandAdapter(private val brands: List<Brand>) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: TextView = itemView.findViewById(R.id.textIdB)
        val textBrandName: TextView = itemView.findViewById(R.id.textBrandName)
        val textCountry: TextView = itemView.findViewById(R.id.textCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_brand, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brands[position]
        holder.textId.text = brand.id.toString()
        holder.textBrandName.text = brand.brand_name
        holder.textCountry.text = brand.country
    }

    override fun getItemCount(): Int {
        return brands.size
    }
}