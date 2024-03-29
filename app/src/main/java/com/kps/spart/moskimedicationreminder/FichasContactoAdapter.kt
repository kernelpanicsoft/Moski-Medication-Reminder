package com.kps.spart.moskimedicationreminder

import MMR.viewModels.FichaContactoViewModel
import MMR.viewModels.MedicoViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.media.Image
import android.opengl.Visibility
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import elements.FichaContacto
import elements.Medico

class FichasContactoAdapter (private val context: Context, private val fichasViewModel : FichaContactoViewModel, private val medicosViewModel : MedicoViewModel) : ListAdapter<FichaContacto, FichasContactoAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {


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
    private lateinit var medicoActual: Medico

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val titulo = v.findViewById<TextView>(R.id.tituloFichaContactoTV)
        val direccion = v.findViewById<TextView>(R.id.direccionMedicoTV)
        val telefono = v.findViewById<TextView>(R.id.telefono1id)
        val celular = v.findViewById<TextView>(R.id.telefono2TV)
        val email = v.findViewById<TextView>(R.id.emailTV)
        val sitioWeb = v.findViewById<TextView>(R.id.websiteTV)
        val moreDots = v.findViewById<ImageView>(R.id.optionsContactCardIcon)
        val scheduleAppointment = v.findViewById<ImageView>(R.id.agendarCitaContactCardIcon)

        val direccionLayout = v.findViewById<LinearLayout>(R.id.DireccionLY)
        val telefonoLayout = v.findViewById<LinearLayout>(R.id.telefonoLY)
        val celularLayout = v.findViewById<LinearLayout>(R.id.celularLY)
        val emailLayout = v.findViewById<LinearLayout>(R.id.emailLY)
        val webLayout = v.findViewById<LinearLayout>(R.id.webSiteLY)

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
        if(!fichaActual.direccion.isNullOrEmpty()){
            holder.direccion.text = fichaActual.direccion
        }else{
            holder.direccionLayout.visibility = View.GONE
        }

        if(!fichaActual.telefono.isNullOrEmpty()){
            holder.telefono.text = fichaActual.telefono
        }else{
            holder.telefonoLayout.visibility = View.GONE
        }

        if(!fichaActual.celular.isNullOrEmpty()){
            holder.celular.text = fichaActual.celular
        }else{
            holder.celularLayout.visibility = View.GONE
        }

        if(!fichaActual.email.isNullOrEmpty()){
            holder.email.text = fichaActual.email
        }else{
            holder.emailLayout.visibility = View.GONE
        }

        if(!fichaActual.sitioweb.isNullOrEmpty()){
            holder.sitioWeb.text = fichaActual.sitioweb
        }else{
            holder.webLayout.visibility = View.GONE
        }


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
        holder.scheduleAppointment.setOnClickListener {
            val nav = Intent(context,AnadirCitaMedicaActivity::class.java)
            nav.putExtra("nombreMedico", medicoActual.nombre)
            nav.putExtra("tituloMedico", medicoActual.titulo)
            nav.putExtra("especialidadMedico", medicoActual.especialidad)
            nav.putExtra("direccionMedico", fichaActual.direccion)

            context.startActivity(nav)
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

    fun setCurrentMedic(medico: Medico){
        medicoActual = medico
    }

}