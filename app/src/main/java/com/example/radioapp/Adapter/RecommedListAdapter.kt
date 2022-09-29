package com.example.radioapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.R

class RecommedListAdapter(val context: Context,val programsList: ArrayList<ListRecommed.Recommed>) : RecyclerView.Adapter<RecommedListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommed_rcy_fragment, parent, false)
        return ViewHolder(view, context)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Article= programsList.get(position)

        holder.txt2.text = Article.genre
        holder.txt1.text = Article.name
        holder.txt3.text= Article.countryName
        // holder.ImageView.setImageBitmap(Article.image)
    }
    override fun getItemCount(): Int {
        return programsList.size
    }
    class ViewHolder(ItemView: View, context: Context) : RecyclerView.ViewHolder(ItemView) {
        val ImageView: ImageView = itemView.findViewById(R.id.img_channel)
        val txt1: TextView = itemView.findViewById(R.id.channel_name)
        val txt2: TextView = itemView.findViewById(R.id.channel_band)
        val txt3: TextView = itemView.findViewById(R.id.channel_courtry)
    }

}