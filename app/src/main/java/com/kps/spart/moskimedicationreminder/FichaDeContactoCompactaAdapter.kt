package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast


class FichaDeContactoCompactaAdapter (private val context : Context, private val fichasContacto : ArrayList<FichaContacto>) : RecyclerView.Adapter<FichaDeContactoCompactaAdapter.ViewHolder>() {
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val titulo = v.findViewById<TextView>(R.id.tituloFichaContactoCompactaTV)
        val editar = v.findViewById<TextView>(R.id.editarFCCTV)
        val eliminar = v.findViewById<TextView>(R.id.eliminarFCCTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_ficha_contacto_compacta, parent, false)

        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.titulo.text = fichasContacto[position].titulo

        holder.editar.setOnClickListener{
            Toast.makeText(holder.editar.context, "Elementos en lista: " + fichasContacto.size + "IndiceAcutal: " + position , Toast.LENGTH_SHORT).show()
        }

        holder.eliminar.setOnClickListener{
            Toast.makeText(holder.eliminar.context,"Indice eliminado:" + position + "Elementos en la lista: " + fichasContacto.size, Toast.LENGTH_SHORT).show()
            fichasContacto.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, fichasContacto.size)
        }
    }

    override fun getItemCount(): Int {
        return fichasContacto.size
    }

}