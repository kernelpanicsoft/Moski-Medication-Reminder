package com.kps.spart.moskimedicationreminder

import android.content.Context
import android.graphics.Color
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
import elements.Medico
import model.MMDContract

/**
 * Created by spart on 20/11/2017.
 */

class MedicosAdapter(context : Context?) : ListAdapter<Medico, MedicosAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Medico>(){
        override fun areItemsTheSame(oldItem: Medico, newItem: Medico): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medico, newItem: Medico): Boolean {
            return oldItem.nombre.equals(newItem.nombre) && oldItem.especialidad.equals(newItem.especialidad)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono = v.findViewById<View>(R.id.iconoMedico) as ImageView
        val titulo = v.findViewById<View>(R.id.TituloDoctorTV) as TextView
        val nombre = v.findViewById<View>(R.id.NombreDoctorTV) as TextView
        val especialidad = v.findViewById<View>(R.id.EspecialidadTV) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicosAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_medico, parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicoActual = getItem(position)

        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.icono.setColorFilter(medicoActual.colorIcono!!)
        holder.titulo.text = medicoActual.titulo
        holder.nombre.text = medicoActual.nombre
        holder.especialidad.text = medicoActual.especialidad

    }


    fun setOnItemClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener?.onClick(v)
    }

    fun getMedicAt(position: Int) : Medico {
        return getItem(position)
    }
}
