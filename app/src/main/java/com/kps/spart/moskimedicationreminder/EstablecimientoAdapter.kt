package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import android.database.Cursor
import android.provider.BaseColumns
import model.MMDContract

/**
 * Created by spart on 03/11/2017.

 */


class EstablecimientoAdapter (private val cursor: Cursor) : RecyclerView.Adapter<EstablecimientoAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null

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

        cursor.moveToPosition(position)

        holder.icono.setImageResource(R.drawable.ic_capsula)
        holder.nombre.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_ESTABLECIMIENTO))
        holder.direccion.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.DIRECCION_ESTABLECIMIENTO))
        holder.telefono.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TELEFONO1_ESTABLECIMIENTO))
    }

    override fun getItemCount(): Int {
        return cursor.count
    }


    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener?.onClick(v)
    }

    fun getEstablishMentID(position: Int) : Int{
        return when(cursor.moveToPosition(position)){
            true -> cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            else -> -1
        }
    }


}

