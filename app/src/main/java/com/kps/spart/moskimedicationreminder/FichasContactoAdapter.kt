package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item_ficha_contacto_llena.*
import model.MMDContract

class FichasContactoAdapter (private val context: Context?, private val cursor: Cursor) : RecyclerView.Adapter<FichasContactoAdapter.ViewHolder>(), View.OnClickListener {


    private var listener: View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val titulo = v.findViewById<TextView>(R.id.tituloFichaContactoTV)
        val direccion = v.findViewById<TextView>(R.id.direccionMedicoTV)
        val telefono = v.findViewById<TextView>(R.id.telefono1id)
        val celular = v.findViewById<TextView>(R.id.telefono2TV)
        val email = v.findViewById<TextView>(R.id.emailTV)
        val sitioWeb = v.findViewById<TextView>(R.id.websiteTV)
        val accesoRapido = v.findViewById<ImageView>(R.id.imageView23)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_ficha_contacto_llena,parent,false)

        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.titulo.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TITULO_FICHA_CONTACTO))
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun setOnClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        if (listener != null) {
            listener!!.onClick(v)

        }
    }


}