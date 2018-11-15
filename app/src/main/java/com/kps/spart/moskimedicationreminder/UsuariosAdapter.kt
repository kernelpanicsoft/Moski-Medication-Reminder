package com.kps.spart.moskimedicationreminder

import Elementos.Usuario
import android.database.Cursor
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import model.MMDContract
import model.mmrbd


class UsuariosAdapter (private var cursor: Cursor) : RecyclerView.Adapter<UsuariosAdapter.ViewHolder>(), View.OnClickListener {
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
        holder.nombre.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_USUARIO))
        holder.apellidos.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.APELLIDOS_USUARIO))
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun setOnClickListener(listener : View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }

    fun swapCursor(newCursor : Cursor){
        cursor = newCursor
        notifyDataSetChanged()
    }

    fun getUserID(position: Int) : Int{
        return when(cursor.moveToPosition(position)){
            true -> cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            else -> -1

        }
    }

    fun getUserName(position: Int) : String{
        return when(cursor.moveToPosition(position)){
            true -> cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_USUARIO))
            else -> ""
        }
    }


}
