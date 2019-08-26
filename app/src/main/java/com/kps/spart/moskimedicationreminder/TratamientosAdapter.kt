package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import elements.JoinMedicamentoTratamientoData

import elements.Tratamiento
import model.EstatusTratamiento


class TratamientosAdapter(private val context: Context?) : ListAdapter<JoinMedicamentoTratamientoData,TratamientosAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener : View.OnClickListener? = null
    private val iconsCollection = context?.resources?.getStringArray(R.array.TipoMedicamento)



    class DIFF_CALLBACK : DiffUtil.ItemCallback<JoinMedicamentoTratamientoData>(){
        override fun areItemsTheSame(oldItem: JoinMedicamentoTratamientoData, newItem: JoinMedicamentoTratamientoData): Boolean {
            return oldItem.titulo.equals(newItem.titulo)
        }

        override fun areContentsTheSame(oldItem: JoinMedicamentoTratamientoData, newItem: JoinMedicamentoTratamientoData): Boolean {
            return oldItem.titulo.equals(newItem.titulo) //&& oldItem.medicamentoID == newItem.medicamentoID
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         val bookmarkIV: ImageView
         val iconoMedicamentoIV: ImageView
         val statusIV: ImageView
         val bookmarkTV: TextView
         val medicamentoTV: TextView
         val statusTV: TextView
         val estatusIV: ImageView

        init {

            bookmarkIV = v.findViewById<View>(R.id.iconoBookmark) as ImageView
            iconoMedicamentoIV = v.findViewById<View>(R.id.MedicamentoIconoTV) as ImageView
            statusIV = v.findViewById<View>(R.id.iconoStatus) as ImageView
            bookmarkTV = v.findViewById<View>(R.id.tagTratamientoTV) as TextView
            medicamentoTV = v.findViewById<View>(R.id.nombreMedicamento) as TextView
            statusTV = v.findViewById<View>(R.id.status_tratamientoTV) as TextView
            estatusIV = v.findViewById(R.id.iconoStatus)
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
        holder.medicamentoTV.text = tratamientoActual.medicamento
        holder.iconoMedicamentoIV.setColorFilter(tratamientoActual.color!!)
        //holder.statusTV.text = tratamientoActual.status.toString()

        val medicineType = tratamientoActual.tipo

        when(iconsCollection?.indexOf(medicineType)){
            0 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_roundpill)}
            1 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_tab)}
            2 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_capsula)}
            3 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_syrup)}
            4 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_drops)}
            5 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_eyedrops)}
            6 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_ointment)}
            7 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_powder)}
            8 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_gel)}
            9 -> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_inhalator)}
            10-> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_suppository)}
            11-> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_intravenous)}
            12-> {holder.iconoMedicamentoIV.setImageResource(R.drawable.ic_syringe)}
        }

     //   holder.statusTV.text = tratamientoActual.estatusTratamiento.toString()

        when(tratamientoActual.estatusTratamiento){
            EstatusTratamiento.ACTIVO ->{
                holder.statusTV.text = context?.getString(R.string.activo)
                holder.estatusIV.setColorFilter(ContextCompat.getColor(context!!,R.color.verde))
            }
            EstatusTratamiento.TERMINADO -> {
                holder.statusTV.text = context?.getString(R.string.terminado)
                holder.estatusIV.setColorFilter(ContextCompat.getColor(context!!,R.color.rojo))
            }
            EstatusTratamiento.PAUSADO -> {
                holder.statusTV.text = context?.getString(R.string.pausado)
                holder.estatusIV.setColorFilter(ContextCompat.getColor(context!!,R.color.orange))
            }
            EstatusTratamiento.PROGRAMADO -> {
                holder.statusTV.text = context?.getString(R.string.programado)
                holder.estatusIV.setColorFilter(ContextCompat.getColor(context!!,R.color.colorPrimaryLight))
            }
        }

    }

    fun setOnClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        listener!!.onClick(v)
    }

    fun getTratamientoAt(position: Int) : JoinMedicamentoTratamientoData{
        return getItem(position)
    }

    fun updateList(search : List<JoinMedicamentoTratamientoData>){
        submitList(search)
        notifyDataSetChanged()
    }
}
