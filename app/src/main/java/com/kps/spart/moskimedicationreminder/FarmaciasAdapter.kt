package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import Elementos.Farmacia
import android.database.Cursor
import android.widget.CursorAdapter
import model.MMDContract


/**
 * Created by spart on 03/11/2017.

 */
const val ID_FARMACIA: Int = 0
const val NOMBRE_FARMACIA: Int = 1
const val DIRECCION_FARMACIA: Int = 2
const val TELEFONO1_FARMACIA: Int = 3
const val TELEFONO2_FARMACIA: Int = 4
const val EMAIL_FARMACIA: Int = 5
const val WEB_FARMCIA: Int = 6
const val LATITUD_FARMACIA: Int = 7
const val LONGITUD_FARMCIA: Int = 8


class FarmaciasAdapter (private val farmacias: Array<Farmacia>, private val cursor: Cursor) : RecyclerView.Adapter<FarmaciasAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null




    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView
        val Nombre: TextView
        val Direccion: TextView
        val Telefono: TextView


        init {
            icono = v.findViewById<View>(R.id.IconoFarmacia) as ImageView
            Nombre = v.findViewById<View>(R.id.NombreTV) as TextView
            Direccion = v.findViewById<View>(R.id.DireccionTV) as TextView
            Telefono = v.findViewById<View>(R.id.TelefonoTV) as TextView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmaciasAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_farmacia, parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.Nombre.text = farmacias?.getString(NOMBRE_FARMACIA)
        holder.Direccion.text = farmacias?.getString(DIRECCION_FARMACIA)
        holder.Telefono.text = farmacias?.getString(TELEFONO1_FARMACIA)
        */

        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.Nombre.text = farmacias[position].nombre
        holder.Direccion.text = farmacias[position].direccion
        holder.Telefono.text = farmacias[position].telefono1
    }

    override fun getItemCount(): Int {
        if(farmacias != null){
            return farmacias!!.size
        }

        return 0

    }


    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {


        if (listener != null) {
            listener!!.onClick(v)
            Toast.makeText(v.context, "On click lejano SN" + cursor.count, Toast.LENGTH_SHORT).show()
        }
    }

    /*
    fun swapCursor(newCursor: Cursor?){
        if(newCursor != null){
            farmacias = newCursor
        }
    }

    fun getCursor() : Cursor?{
        return farmacias
    }

    */


}

