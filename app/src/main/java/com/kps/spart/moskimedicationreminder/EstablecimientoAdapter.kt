package com.kps.spart.moskimedicationreminder

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import android.support.v7.util.DiffUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import elements.Establecimiento
import model.MMDContract


class EstablecimientoAdapter (context: Context?) : ListAdapter<Establecimiento,EstablecimientoAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener: View.OnClickListener? = null
    private val iconsCollection = context?.resources?.getStringArray(R.array.tipo_establecimiento)

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Establecimiento>(){
        override fun areItemsTheSame(oldItem: Establecimiento, newItem: Establecimiento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Establecimiento, newItem: Establecimiento): Boolean {
            return oldItem.nombre.equals(newItem.nombre) && oldItem.tipo.equals(newItem.tipo)
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

        when(iconsCollection?.indexOf(establecimientoActual.tipo)){
            0 ->{holder.icono.setImageResource(R.drawable.ic_pharmacy)}
            1 ->{holder.icono.setImageResource(R.drawable.ic_medic_lab)}
            2 ->{holder.icono.setImageResource(R.drawable.ic_xray_lab)}
            3 ->{holder.icono.setImageResource(R.drawable.ic_crutch)}
        }

        holder.icono.setColorFilter(ContextCompat.getColor(holder.icono.context,R.color.colorPrimary))
        holder.nombre.text = establecimientoActual.nombre
        holder.direccion.text = establecimientoActual.direccion
        holder.telefono.text = establecimientoActual.telefono1

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


    fun updateList(search: List<Establecimiento>){
        submitList(search)
        notifyDataSetChanged()
    }

}

