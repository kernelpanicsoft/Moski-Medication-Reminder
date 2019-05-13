package com.kps.spart.moskimedicationreminder

import android.support.v7.app.AlertDialog
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import elements.Toma

class DetallesTomaAdapter : ListAdapter<Toma, DetallesTomaAdapter.ViewHolder>(DIFF_CALLBACK()){

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Toma>(){
        override fun areItemsTheSame(oldItem: Toma, newItem: Toma): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Toma, newItem: Toma): Boolean {
            return oldItem.horaToma.equals(newItem.horaToma)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val horaToma = v.findViewById<TextView>(R.id.horaTomaTV)
        val iconoEliminar = v.findViewById<ImageView>(R.id.deleteShootIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DetallesTomaAdapter.ViewHolder{
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_detalles_toma, parent, false)


        return ViewHolder(v)
    }



    override fun onBindViewHolder(holder: DetallesTomaAdapter.ViewHolder, position: Int){
        val tomaActual = getItem(position)
        holder.horaToma.text = tomaActual.horaToma

    }



    fun getShootAt(position: Int) : Toma{
        return getItem(position)
    }

}