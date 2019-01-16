package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import elements.Medicamento
import model.MMDContract

class MedicamentosAdapter(context: Context?): ListAdapter<Medicamento, MedicamentosAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    private val iconsCollection = context?.resources?.getStringArray(R.array.TipoMedicamento)

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Medicamento>(){
        override fun areItemsTheSame(oldItem: Medicamento, newItem: Medicamento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medicamento, newItem: Medicamento): Boolean {
            return oldItem.nombreMedicamento.equals(newItem.nombreMedicamento)
        }
    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView = v.findViewById(R.id.MedicamentoIconoTV)
        val NombreComercial : TextView = v.findViewById(R.id.nombreMedicamento)
        val NombreGenerico: TextView = v.findViewById(R.id.nombreComercialMedicamento)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_medicamento, parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicamentoActual = getItem(position)

        val medicineType = medicamentoActual.tipo

        when(iconsCollection?.indexOf(medicineType)){
            0 -> {holder.icono.setImageResource(R.drawable.ic_roundpill)}
            1 -> {holder.icono.setImageResource(R.drawable.ic_tab)}
            2 -> {holder.icono.setImageResource(R.drawable.ic_capsula)}
            3 -> {holder.icono.setImageResource(R.drawable.ic_syrup)}
            4 -> {holder.icono.setImageResource(R.drawable.ic_drops)}
            5 -> {holder.icono.setImageResource(R.drawable.ic_eyedrops)}
            6 -> {holder.icono.setImageResource(R.drawable.ic_ointment)}
            7 -> {holder.icono.setImageResource(R.drawable.ic_powder)}
            8 -> {holder.icono.setImageResource(R.drawable.ic_gel)}
            9 -> {holder.icono.setImageResource(R.drawable.ic_inhalator)}
            10-> {holder.icono.setImageResource(R.drawable.ic_suppository)}
            11-> {holder.icono.setImageResource(R.drawable.ic_intravenous)}
            12-> {holder.icono.setImageResource(R.drawable.ic_syringe)}
        }

        holder.icono.setColorFilter(medicamentoActual.color!!)

        holder.NombreGenerico.text = medicamentoActual.nombreGenerico
        holder.NombreComercial.text = medicamentoActual.nombreMedicamento

    }



    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener?.onClick(v)
    }

    fun getMedicamentoAt(position : Int) : Medicamento{
        return getItem(position)
    }

}
