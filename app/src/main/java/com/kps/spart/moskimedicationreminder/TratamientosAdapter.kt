package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import elements.Tratamiento

/**
 * Created by spart on 20/12/2017.
 */

class TratamientosAdapter(private val context: Context?) : ListAdapter<Tratamiento,TratamientosAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener : View.OnClickListener? = null


    class DIFF_CALLBACK : DiffUtil.ItemCallback<Tratamiento>(){
        override fun areItemsTheSame(oldItem: Tratamiento, newItem: Tratamiento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tratamiento, newItem: Tratamiento): Boolean {
            return oldItem.titulo.equals(newItem.titulo) && oldItem.medicamentoID == newItem.medicamentoID
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): TratamientosAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_tratamiento, parent, false)
                v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tratamientoActual = getItem(position)
        holder.bookmarkTV.text = tratamientoActual.titulo
        holder.medicamentoTV.text = tratamientoActual.id.toString()
        holder.statusTV.text = tratamientoActual.status.toString()
    }

    fun setOnClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        listener!!.onClick(v)
    }

    fun getTratamientoAt(position: Int) : Tratamiento{
        return getItem(position)
    }
}
