package com.example.tfg.Modelo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.R
import com.squareup.picasso.Picasso

class AdaptadorListaLigas(private  val elementos:List<OpcionLiga>,private val onItemClick:(Int)-> Unit): RecyclerView.Adapter<AdaptadorListaLigas.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvNombre=view.findViewById<TextView>(R.id.tvLiga)
        val ivLogo=view.findViewById<ImageView>(R.id.ivLogo)
        val ivPais=view.findViewById<ImageView>(R.id.ivPais)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.ligaopcion,parent,false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elemento=elementos[position]
        holder.tvNombre.text=elemento.nombre
        Picasso.get().load(elemento.log).into(holder.ivLogo)
        Picasso.get().load(elemento.paisurl).into(holder.ivPais)
        holder.itemView.setOnClickListener {
            onItemClick(elemento.id)
        }
    }

    override fun getItemCount(): Int=elementos.size


}