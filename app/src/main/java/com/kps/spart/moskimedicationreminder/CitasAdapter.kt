package com.kps.spart.moskimedicationreminder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.text.SimpleDateFormat

import Elementos.CitaMedica

/**
 * Created by spart on 15/12/2017.
 */

class CitasAdapter(private val items: Array<CitaMedica>) : RecyclerView.Adapter<CitasAdapter.ViewHolder>(), View.OnClickListener{
    private val sdf = SimpleDateFormat("dd/MM/yyyy, h:mm a")
    var listener: View.OnClickListener? = null


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val esfera: ImageView
        val iconoUbicacion: ImageView
        val iconoDoctor: ImageView
        val iconoFecha: ImageView
        val TituloTV: TextView
        val DoctorTV: TextView
        val DireccionTV: TextView
        val FechaTV: TextView

        init {

            esfera = v.findViewById<View>(R.id.imageView4) as ImageView
            iconoUbicacion = v.findViewById<View>(R.id.imageView5) as ImageView
            iconoDoctor = v.findViewById<View>(R.id.imageView6) as ImageView
            iconoFecha = v.findViewById<View>(R.id.imageView7) as ImageView

            TituloTV = v.findViewById<View>(R.id.textView4) as TextView
            DoctorTV = v.findViewById<View>(R.id.medictoCitaTV) as TextView
            DireccionTV = v.findViewById<View>(R.id.direccionCitaTV) as TextView
            FechaTV = v.findViewById<View>(R.id.fechaCitaTV) as TextView

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): CitasAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cita_medica, parent, false)
        v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // holder.esfera.setColorFilter(Color.parseColor(items[position].color))
       // holder.iconoFecha.setColorFilter(Color.parseColor(items[position].color))
      //  holder.iconoDoctor.setColorFilter(Color.parseColor(items[position].color))
      //  holder.iconoUbicacion.setColorFilter(Color.parseColor(items[position].color))

        holder.TituloTV.text = items[position].titulo
        holder.DoctorTV.text = items[position].doctor
        holder.DireccionTV.text = items[position].ubicacion
        //        holder.FechaTV.text = sdf.format(items[position].fechaYhora)

    }

    fun setOnItemClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        if(listener != null){
            listener!!.onClick(v)
        }
    }


}
