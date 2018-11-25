package com.kps.spart.moskimedicationreminder
import Elementos.Toma
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class HorarioAdapter(private val items: Array<Toma>) : RecyclerView.Adapter<HorarioAdapter.ViewHolder>(), View.OnClickListener{
    private var listener : View.OnClickListener? = null

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
        holder.iconoMedicamento.setImageResource(R.drawable.ic_capsula)
        holder.iconoTratamiento.setImageResource(R.drawable.ic_bookmark)
        holder.horaToma.text=items[position].horaToma
        holder.nombreTratamientoTV.text = items[position].nombreTratamiento
        holder.nombreMedicamentoTV.text = items[position].nombreMedicamento
    }

    override fun getItemCount(): Int{
        return items.size
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