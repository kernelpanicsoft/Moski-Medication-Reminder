package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import Elementos.Tratamiento

/**
 * Created by spart on 20/12/2017.
 */

class TratamientosAdapter(private val items: Array<Tratamiento>) : RecyclerView.Adapter<TratamientosAdapter.ViewHolder>(), View.OnClickListener {
    private var listener : View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         val bookmarkIV: ImageView
         val iconoMedicamentoIV: ImageView
         val statusIV: ImageView
         val bookmarkTV: TextView
         val medicamentoTV: TextView
         val statusTV: TextView

        init {

            bookmarkIV = v.findViewById<View>(R.id.iconoBookmark) as ImageView
            iconoMedicamentoIV = v.findViewById<View>(R.id.MedicamentoIconoTV) as ImageView
            statusIV = v.findViewById<View>(R.id.iconoStatus) as ImageView
            bookmarkTV = v.findViewById<View>(R.id.tagTratamientoTV) as TextView
            medicamentoTV = v.findViewById<View>(R.id.nombreMedicamento) as TextView
            statusTV = v.findViewById<View>(R.id.status_tratamientoTV) as TextView
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): TratamientosAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tratamiento, parent, false)
                v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bookmarkTV.text = items[position].nombreTratamiento
        holder.medicamentoTV.text = items[position].medicamento
        holder.statusTV.text = items[position].status
    }

    fun setOnClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        listener!!.onClick(v)
    }
}
