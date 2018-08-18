package com.kps.spart.moskimedicationreminder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import Elementos.Medico

/**
 * Created by spart on 20/11/2017.
 */

class MedicosAdapter(private val medicos: Array<Medico>) : RecyclerView.Adapter<MedicosAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView
        val titulo: TextView
        val nombre: TextView
        val especialidad: TextView
        val telefono: TextView

        init {

            icono = v.findViewById<View>(R.id.iconoMedico) as ImageView
            titulo = v.findViewById<View>(R.id.TituloDoctorTV) as TextView
            nombre = v.findViewById<View>(R.id.NombreDoctorTV) as TextView
            especialidad = v.findViewById<View>(R.id.EspecialidadTV) as TextView
            telefono = v.findViewById<View>(R.id.TelefonoDoctorTV) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicosAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_medico, parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.icono.setColorFilter(Color.parseColor(medicos[position].colorIcono))
        holder.titulo.text = medicos[position].titulo
        holder.nombre.text = medicos[position].nombreMedico
        holder.especialidad.text = medicos[position].especialidad
        holder.telefono.text = medicos[position].telefono1

    }

    override fun getItemCount(): Int {
        return medicos.size
    }

    fun setOnItemClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (listener != null) {
            listener!!.onClick(v)
        }
    }
}
