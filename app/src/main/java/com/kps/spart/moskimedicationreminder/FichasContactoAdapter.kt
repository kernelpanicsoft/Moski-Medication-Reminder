package com.kps.spart.moskimedicationreminder

import MMR.viewModels.FichaContactoViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import elements.FichaContacto

class FichasContactoAdapter (private val context: Context, private val fichasViewModel : FichaContactoViewModel) : ListAdapter<FichaContacto, FichasContactoAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {

    class DIFF_CALLBACK : DiffUtil.ItemCallback<FichaContacto>(){
        override fun areItemsTheSame(oldItem: FichaContacto, newItem: FichaContacto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FichaContacto, newItem: FichaContacto): Boolean {
            return oldItem.titulo.equals(newItem.titulo) &&
                    oldItem.direccion.equals(newItem.direccion) &&
                    oldItem.telefono.equals(newItem.telefono) &&
                    oldItem.celular.equals(newItem.celular) &&
                    oldItem.email.equals(newItem.email) &&
                    oldItem.sitioweb.equals(newItem.sitioweb)
        }
    }
    private var listener: View.OnClickListener? = null


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val titulo = v.findViewById<TextView>(R.id.tituloFichaContactoTV)
        val direccion = v.findViewById<TextView>(R.id.direccionMedicoTV)
        val telefono = v.findViewById<TextView>(R.id.telefono1id)
        val celular = v.findViewById<TextView>(R.id.telefono2TV)
        val email = v.findViewById<TextView>(R.id.emailTV)
        val sitioWeb = v.findViewById<TextView>(R.id.websiteTV)
        val accesoRapido = v.findViewById<ImageView>(R.id.imageView23)
        val moreDots = v.findViewById<ImageView>(R.id.optionsContactCardIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_ficha_contacto_llena,parent,false)

        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fichaActual = getItem(position)

        holder.titulo.text = fichaActual.titulo
        holder.direccion.text = fichaActual.direccion
        holder.telefono.text = fichaActual.telefono
        holder.celular.text = fichaActual.celular
        holder.email.text = fichaActual.email
        holder.sitioWeb.text = fichaActual.sitioweb
        holder.moreDots.setOnClickListener {
            val popup = PopupMenu(holder.moreDots.context,it)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.menu_edit_delete,popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                 when(menuItem.itemId){
                    R.id.edit_card ->{
                        val editCard = Intent(context,AnadirFichaContactoActivity::class.java)
                        editCard.putExtra("CARD_ID", getItem(position).id)
                        context.startActivity(editCard)
                        true
                    }
                    R.id.delete_card ->{
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle(context.getString(R.string.eliminar_ficha_de_contacto))
                                .setMessage(context.getString(R.string.esta_seguro_que_desea_eliminar_ficha_contacto))
                                .setPositiveButton(context.getString(R.string.si)){ dialog, which ->
                                    fichasViewModel.delete(getItem(position))
                                }
                                .setNegativeButton(context.getString(R.string.no)){ dialog, which ->}
                        val dialog = builder.create()
                        dialog.show()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }


    }

    fun getFichaContactoAt(position: Int) : FichaContacto{
        return getItem(position)
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