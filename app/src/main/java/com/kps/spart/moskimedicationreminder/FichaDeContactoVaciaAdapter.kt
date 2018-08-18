package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.list_item_ficha_contacto_llena.*

class FichaDeContactoVaciaAdapter (private val fichasContacto: ArrayList<FichaContacto>) : RecyclerView.Adapter<FichaDeContactoVaciaAdapter.ViewHolder>(), View.OnClickListener {


    private var listener: View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val titulo = v.findViewById<TextView>(R.id.textInputLayoutficha)
        val direccion = v.findViewById<TextView>(R.id.textInputLayoutDireccion)
        val telefono = v.findViewById<TextView>(R.id.textInputLayoutTelefono)
        val celular = v.findViewById<TextView>(R.id.textInputLayoutCelular)
        val email = v.findViewById<TextView>(R.id.textInputLayoutEmail)
        val sitioWeb = v.findViewById<TextView>(R.id.textInputLayoutWebSite)
        val accesoRapido = v.findViewById<CheckBox>(R.id.checkboxFav)
        val remover = v.findViewById<TextView>(R.id.removerFicha)
        val anadirFicha = v.findViewById<Button>(R.id.anadirFichaButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_ficha_contacto,parent,false)

        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titulo.text = fichasContacto[position].titulo
        holder.direccion.text = fichasContacto[position].direccion
        holder.telefono.text = fichasContacto[position].telefono
        holder.celular.text = fichasContacto[position].celular
        holder.email.text = fichasContacto[position].email
        holder.sitioWeb.text = fichasContacto[position].sitioweb
        holder.accesoRapido.isChecked = fichasContacto[position].accesoRapido

        holder.accesoRapido.setOnClickListener(
                View.OnClickListener {
                    Toast.makeText(holder.accesoRapido.context,"Posicion :" + position + "Tamaño del holder " + (fichasContacto.size ),Toast.LENGTH_SHORT).show()
                    setQuickActionEnable(position)
                }
        )

        if(position == 0 && fichasContacto.size == 1)
        {
            holder.remover.visibility = View.GONE
        }else{
            holder.remover.visibility = View.VISIBLE
        }

        holder.remover.setOnClickListener(
                View.OnClickListener {
                    Toast.makeText(holder.anadirFicha.context,"Posicion eliminada" + position + " " + (fichasContacto.size ),Toast.LENGTH_SHORT).show()
                    removeItem(position)
                }
        )


        if(position ==  fichasContacto.size - 1 ) {
            holder.anadirFicha.visibility = View.VISIBLE
        }else{
            holder.anadirFicha.visibility = View.GONE
        }

        holder.anadirFicha.setOnClickListener(
                View.OnClickListener{
                   // if(holder.titulo.text.isBlank() && (holder.direccion.text.isBlank()  || holder.telefono.text.isBlank()) || holder.celular.text.isBlank() || holder.email.text.isBlank() || holder.sitioWeb.text.isBlank()) {
                  //      Toast.makeText(holder.anadirFicha.context,R.string.alertaFichaVacia,Toast.LENGTH_LONG).show()
                  //  }else{
                        fichasContacto[position].titulo = holder.titulo.text.toString()
                        fichasContacto[position].direccion = holder.direccion.text.toString()
                        fichasContacto[position].telefono = holder.telefono.text.toString()
                        fichasContacto[position].celular = holder.celular.text.toString()
                        fichasContacto[position].email = holder.email.text.toString()
                        fichasContacto[position].sitioweb = holder.sitioWeb.text.toString()
                        fichasContacto[position].accesoRapido = holder.accesoRapido.isChecked
                        Toast.makeText(holder.anadirFicha.context,"Texto de los controles: " + holder.titulo.text + ", " + holder.direccion.text, Toast.LENGTH_SHORT).show()
                        fichasContacto.add(FichaContacto())
                        notifyDataSetChanged()
                 //   }
                }
        )


    }

    override fun getItemCount(): Int {
        return fichasContacto.size
    }

    fun setOnClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        if (listener != null) {
            listener!!.onClick(v)

        }
    }

    fun addItem(ficha : FichaContacto){
        fichasContacto.add(ficha)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        Log.d("Datos en el arreglo: ", fichasContacto.toString())
        fichasContacto.removeAt(position)
        //notifyItemRemoved(position)
        notifyDataSetChanged()


    }

    fun getLastAddedItem() : FichaContacto{
        return fichasContacto.last()
    }

    fun setLastAddedItem(titulo : String = "",  direccion : String = "", telefono : String = "",  celular : String = "",  email : String = "",  sitioweb : String = "",  accesoRapido : Boolean = false){
        getLastAddedItem().titulo = titulo
        getLastAddedItem().direccion = direccion
        getLastAddedItem().telefono = telefono
        getLastAddedItem().celular = celular
        getLastAddedItem().email = email
        getLastAddedItem().sitioweb = sitioweb
        getLastAddedItem().accesoRapido = accesoRapido
    }

    fun setQuickActionEnable(position: Int){
        for(i in fichasContacto.indices){
            if(i != position)
                fichasContacto[i].accesoRapido = false
                notifyItemChanged(position)
        }
    }




}