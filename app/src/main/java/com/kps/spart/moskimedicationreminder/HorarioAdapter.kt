package com.kps.spart.moskimedicationreminder
import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import elements.Toma
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import elements.JoinTomasDelDia

class HorarioAdapter(private val context: Context?) : ListAdapter<JoinTomasDelDia,HorarioAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener{
    private var listener : View.OnClickListener? = null
    private val iconsCollection = context?.resources?.getStringArray(R.array.TipoMedicamento)

    class DIFF_CALLBACK : DiffUtil.ItemCallback<JoinTomasDelDia>(){
        override fun areItemsTheSame(oldItem: JoinTomasDelDia, newItem: JoinTomasDelDia): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JoinTomasDelDia, newItem: JoinTomasDelDia): Boolean {
            return oldItem.horaToma.equals(newItem.horaToma) && oldItem.tituloTratamiento.equals(newItem.tituloTratamiento)
        }
    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val iconoMedicamento: ImageView
        val iconoTratamiento: ImageView
        val nombreMedicamentoTV: TextView
        val nombreTratamientoTV: TextView
        val horaToma: TextView

        init{
            iconoMedicamento = v.findViewById(R.id.MedicamentoIconoTV)
            iconoTratamiento = v.findViewById(R.id.iconoTrataMiento)
            nombreTratamientoTV = v.findViewById(R.id.textViewTratamiento)
            nombreMedicamentoTV = v.findViewById(R.id.textViewMedicamento)
            horaToma = v.findViewById(R.id.textViewHoraToma)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : HorarioAdapter.ViewHolder{
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_toma,parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val tomaActual = getItem(position)
        holder.iconoMedicamento.setColorFilter(tomaActual.color!!)
        holder.iconoTratamiento.setImageResource(R.drawable.ic_bookmark)
        holder.horaToma.text=tomaActual.horaToma
        holder.nombreTratamientoTV.text = tomaActual.tituloTratamiento
        holder.nombreMedicamentoTV.text = tomaActual.medicamento


        val medicineType = tomaActual.tipo

        when(iconsCollection?.indexOf(medicineType)){
            0 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_roundpill)}
            1 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_tab)}
            2 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_capsula)}
            3 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_syrup)}
            4 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_drops)}
            5 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_eyedrops)}
            6 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_ointment)}
            7 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_powder)}
            8 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_gel)}
            9 -> {holder.iconoMedicamento.setImageResource(R.drawable.ic_inhalator)}
            10-> {holder.iconoMedicamento.setImageResource(R.drawable.ic_suppository)}
            11-> {holder.iconoMedicamento.setImageResource(R.drawable.ic_intravenous)}
            12-> {holder.iconoMedicamento.setImageResource(R.drawable.ic_syringe)}
        }
    }

    fun setOnClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        if (listener != null){
            listener!!.onClick(v)
            }
    }
}