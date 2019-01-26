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

class HorarioAdapter : ListAdapter<Toma,HorarioAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener{
    private var listener : View.OnClickListener? = null

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Toma>(){
        override fun areItemsTheSame(oldItem: Toma, newItem: Toma): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Toma, newItem: Toma): Boolean {
            return oldItem.horaToma.equals(newItem.horaToma) && oldItem.tratamientoID == newItem.tratamientoID
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
        holder.iconoMedicamento.setImageResource(R.drawable.ic_capsula)
        holder.iconoTratamiento.setImageResource(R.drawable.ic_bookmark)
        holder.horaToma.text=tomaActual.horaToma
        holder.nombreTratamientoTV.text = tomaActual.tratamientoID.toString()

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