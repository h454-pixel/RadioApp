package com.example.radioapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.radioapp.R
import com.example.radioapp.fragment.RadioFragment
import com.example.radioapp.PlayActivity


class RadioListAdapter(val context: Context): RecyclerView.Adapter<RadioListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.radio_rcy_adapter, parent, false)
        return ViewHolder(view, context)
    }

    // binds the list items to a view
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Article=  RadioFragment.programsList.get(position)

        holder.txt2.text = Article.genre
        holder.txt1.text = Article.name
        holder.txt3.text= Article.country_name

        holder.layout.setOnClickListener {
            val intent: Intent = Intent(context,  PlayActivity::class.java)

            intent.putExtra("link", Article.st_link)
            intent.putExtra("id",Article.st_id)
            intent.putExtra("bool", true)
            context.startActivity(intent)
        }

        Glide.with(holder.itemView.getContext())
            .load(Article.image)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_play_arrow_24)
            .into(holder.ImageView);

    }
    override fun getItemCount(): Int {
        return  RadioFragment.programsList.size
    }
    class ViewHolder(ItemView: View, context: Context) : RecyclerView.ViewHolder(ItemView) {
        val ImageView:ImageView= itemView.findViewById(R.id.img_channel)
        val txt1:TextView = itemView.findViewById(R.id.channel_name)
        val txt2:TextView = itemView.findViewById(R.id.channel_band)
        val txt3:TextView = itemView.findViewById(R.id.channel_courtry)
        val layout:ConstraintLayout = itemView.findViewById(R.id.constraint1)

    }



}