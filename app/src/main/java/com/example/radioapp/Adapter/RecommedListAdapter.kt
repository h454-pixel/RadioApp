package com.example.radioapp.Adapter

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
import com.example.radioapp.Model.ListRecommed
import com.example.radioapp.PlayActivity
import com.example.radioapp.fragment.RecomdFragment
import com.example.radioapp.R

class RecommedListAdapter(val context: Context,val programsList: ArrayList<ListRecommed.Recommed>) : RecyclerView.Adapter<RecommedListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommed_rcy_adapter, parent, false)
        return ViewHolder(view, context)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Article2= programsList.get(position)

        holder.txt2.text = Article2.genre
        holder.txt1.text = Article2.name
   //     holder.txt3.text= Article.countryName

        holder.layout.setOnClickListener {
            val intent: Intent = Intent(context,  PlayActivity::class.java)
            intent.putExtra("link2", Article2.stLink)
            intent.putExtra("id",Article2.stId)
            intent.putExtra("title",Article2.name)
            intent.putExtra("rig",Article2.genre)
            intent.putExtra("bool", true)
            context.startActivity(intent)
        }


        Glide.with(holder.itemView.getContext())
            .load(Article2.image)
            .placeholder(R.drawable.ic_baseline_play_arrow_24)
            .into(holder.ImageView);

    }
    override fun getItemCount(): Int {
        return  programsList.size
    }
    class ViewHolder(ItemView: View, context: Context) : RecyclerView.ViewHolder(ItemView) {
        val ImageView: ImageView = itemView.findViewById(R.id.img_channel)
        val txt1: TextView = itemView.findViewById(R.id.channel_name)
        val txt2: TextView = itemView.findViewById(R.id.channel_band)
       // val txt3: TextView = itemView.findViewById(R.id.channel_courtry)
        val layout: ConstraintLayout = itemView.findViewById(R.id.constraint1)
    }

}