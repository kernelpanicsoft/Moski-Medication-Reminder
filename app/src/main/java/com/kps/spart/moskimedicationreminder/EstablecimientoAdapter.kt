package com.kps.spart.moskimedicationreminder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import android.database.Cursor
import android.provider.BaseColumns
import android.support.v7.util.DiffUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import elements.Establecimiento
import model.MMDContract

/**
 * Created by spart on 03/11/2017.

 */


class EstablecimientoAdapter (context: Context?) : ListAdapter<Establecimiento,EstablecimientoAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Establecimiento>(){
        override fun areItemsTheSame(oldItem: Establecimiento, newItem: Establecimiento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Establecimiento, newItem: Establecimiento): Boolean {
            return oldItem.nombre.equals(newItem.nombre)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView = v.findViewById(R.id.IconoFarmacia)
        val nombre: TextView = v.findViewById(R.id.NombreTV)
        val direccion: TextView = v.findViewById(R.id.DireccionTV)
        val telefono: TextView = v.findViewById(R.id.TelefonoTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablecimientoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_establecimiento, parent, false)
        v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val establecimientoActual = getItem(position)

        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.nombre.text = establecimientoActual.nombre
        holder.direccion.text = establecimientoActual.direccion

    }

    fun getEstablecimientoAt(position: Int) : Establecimiento{
        return getItem(position)
    }


    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener?.onClick(v)
    }


}

