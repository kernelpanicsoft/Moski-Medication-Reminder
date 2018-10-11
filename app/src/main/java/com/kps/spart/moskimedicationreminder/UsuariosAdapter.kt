package com.kps.spart.moskimedicationreminder

import Elementos.Usuario
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import model.MMDContract

const val ID_USUARIO = 0
const val NOMBRE_USUARIO = 1
const val APELLIDOS_USUARIO = 2
const val EDAD_USUARIO = 3
const val GENERO_USUARIO = 4
const val PASSWORD_USUARIO = 5
const val IMAGEN_USUARIO = 6
const val EMAIL_RECUPERAICION = 7


class UsuariosAdapter (private val cursor: Cursor) : RecyclerView.Adapter<UsuariosAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null



    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val icono : ImageView = v.findViewById(R.id.UsuarioIV)
        val nombre : TextView = v.findViewById(R.id.NombreUsuarioTV) as TextView
        val apellidos : TextView = v.findViewById(R.id.ApellidosUsuarioTV) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_usuario,parent,false)

                v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)

        holder.icono.setImageResource(R.drawable.ic_user)
        holder.nombre.text = cursor.getString(NOMBRE_USUARIO)
        holder.apellidos.text = cursor.getString(APELLIDOS_USUARIO)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun setOnClickListner(listener : View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }


}
