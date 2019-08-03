package com.kps.spart.moskimedicationreminder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.text.SimpleDateFormat


import android.support.v7.util.DiffUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import elements.CitaMedica
import model.MMDContract

/**
 * Created by spart on 15/12/2017.
 */

class CitasAdapter(private val context : Context?) : ListAdapter<CitaMedica,CitasAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener{
    private val auxSDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val sdf = SimpleDateFormat.getDateTimeInstance()
    var listener: View.OnClickListener? = null

    class DIFF_CALLBACK : DiffUtil.ItemCallback<CitaMedica>(){
        override fun areItemsTheSame(oldItem: CitaMedica, newItem: CitaMedica): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CitaMedica, newItem: CitaMedica): Boolean {
            return oldItem.titulo.equals(newItem.titulo) && oldItem.fecha.equals(newItem.fecha) && oldItem.hora.equals(newItem.hora)
        }

    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val esfera: ImageView = v.findViewById(R.id.imageView4)
        val TituloTV: TextView = v.findViewById(R.id.textView4)
        val DoctorTV: TextView = v.findViewById(R.id.medictoCitaTV)
        val DireccionTV: TextView =  v.findViewById(R.id.direccionCitaTV)
        val FechaTV: TextView = v.findViewById(R.id.fechaCitaTV)

    }


    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): CitasAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cita_medica, parent, false)
        v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val citaMedicaActual = getItem(position)

        holder.esfera.setColorFilter(citaMedicaActual.color!!)
        holder.TituloTV.text = citaMedicaActual.titulo
        holder.DoctorTV.text = citaMedicaActual.doctor
        holder.DireccionTV.text = citaMedicaActual.ubicacion

        val auxDate =  auxSDF.parse(citaMedicaActual.fecha +  " " + citaMedicaActual.hora)
        val auxDateString = sdf.format(auxDate)
        holder.FechaTV.text = auxDateString

    }


    fun setOnItemClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        listener?.onClick(v)
    }

    fun getCitaMedicaAt(position: Int) : CitaMedica{
        return getItem(position)
    }




}
