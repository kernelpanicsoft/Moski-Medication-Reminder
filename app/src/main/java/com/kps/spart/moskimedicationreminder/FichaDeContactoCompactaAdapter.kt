package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
            val builder = AlertDialog.Builder(holder.editar.context)
            val inflater = LayoutInflater.from(holder.editar.context)
            val inflatedView = inflater.inflate(R.layout.list_item_ficha_contacto, null)


            builder.setTitle(holder.editar.context.getString(R.string.editar_ficha_contacto))
                    .setView(inflatedView)
                    .setPositiveButton(holder.editar.context.getString(R.string.guardar),null)
                    .setNegativeButton(holder.editar.context.getString(R.string.cancelar)){ dialog, which ->  }

            val dialog = builder.create()
            dialog.setOnShowListener{
                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener{
                    val fichaContactoAEditar = fichasContacto[position]

                    val tituloFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutTituloficha)
                    val direccionFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutDireccion)
                    val telefonoFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutTelefono)
                    val celularFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutTelefono)
                    val emailFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutEmail)
                    val sitioWebFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutWebSite)

                    tituloFichaContactoET.setText(holder.editar.context.getString(R.string.colorDistintivo))


                    dialog.dismiss()
                }



            }

            dialog.show()
        }

        holder.eliminar.setOnClickListener{

            fichasContacto.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, fichasContacto.size)
        }
    }

    override fun getItemCount(): Int {
        return fichasContacto.size
    }

}