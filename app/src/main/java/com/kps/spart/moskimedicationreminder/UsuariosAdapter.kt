package com.kps.spart.moskimedicationreminder

import Elementos.Usuario
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class UsuariosAdapter (private val usuario: Array<Usuario>,  private val cursor: Cursor) : RecyclerView.Adapter<UsuariosAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val icono : ImageView
        val nombre : TextView
        val apellidos : TextView

        init{
            icono = v.findViewById(R.id.UsuarioIV) as ImageView
            nombre = v.findViewById(R.id.NombreUsuarioTV) as TextView
            apellidos = v.findViewById(R.id.ApellidosUsuarioTV) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_usuario,parent,false)

                v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icono.setImageResource(R.drawable.ic_user)
        holder.nombre.text = usuario[position].nombre
        holder.apellidos.text = usuario[position].apellidos
    }

    override fun getItemCount(): Int {
        return usuario.size
    }

    fun setOnClickListner(listener : View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }
}
