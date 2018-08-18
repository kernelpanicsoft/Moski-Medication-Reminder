package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import Elementos.Medicamento

/**
 * Created by spart on 27/10/2017.
 */

class MedicamentosAdapter(private val items: Array<Medicamento>) : RecyclerView.Adapter<MedicamentosAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView
        val NombreComercial: TextView
        val Nombre: TextView

        init {

            icono = v.findViewById<View>(R.id.iconoMedicamento) as ImageView
            Nombre = v.findViewById<View>(R.id.nombreMedicamento) as TextView
            NombreComercial = v.findViewById<View>(R.id.nombreComercialMedicamento) as TextView

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentosAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_medicamento, parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.Nombre.text = items[position].nombreMedicamento
        holder.NombreComercial.text = items[position].nombreComercial

    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (listener != null) {
            listener!!.onClick(v)
        }
    }

}
