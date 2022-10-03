package com.example.radioapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.radioapp.R
import com.example.radioapp.Model.ListCountry

class CountryListAdapter(val context: Context, val programsList: ArrayList<ListCountry.Country>): RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_rcy_adapter, parent, false)
        return ViewHolder(view, context)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Article= programsList.get(position)
        holder.txt1.text = Article.c_name

        Glide.with(holder.itemView.getContext())
            .load(Article.image)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_play_arrow_24)
            .into(holder.img);
    }


    override fun getItemCount(): Int {
        return programsList.size
    }


    class ViewHolder(ItemView: View, context: Context) : RecyclerView.ViewHolder(ItemView) {

        val img: ImageView = itemView.findViewById(R.id.img_channel)
        val txt1: TextView = itemView.findViewById(R.id.channel_name)
    }
    }
