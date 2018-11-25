package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import Elementos.Medicamento
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.support.v4.content.ContextCompat
import model.MMDContract

class MedicamentosAdapter(private var context: Context?, private val cursor: Cursor) : RecyclerView.Adapter<MedicamentosAdapter.ViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null
    private val IconsCollection = context?.resources?.getStringArray(R.array.TipoMedicamento)


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView = v.findViewById(R.id.MedicamentoIconoTV)
        val NombreComercial : TextView = v.findViewById(R.id.nombreMedicamento)
        val NombreGenerico: TextView = v.findViewById(R.id.nombreComercialMedicamento)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentosAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_medicamento, parent, false)
        v.setOnClickListener(this)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        val medicineType = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TIPO_MEDICAMENTO))

        when(IconsCollection?.indexOf(medicineType)){
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

        //holder.icono.colorFilter(ContextCompat.getColor(holder.itemView.context,R.color.flamingo)

        holder.NombreComercial.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_COMERCIAL_MEDICAMENTO))
        holder.NombreGenerico.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_GENERICO_MEDICAMENTO))
    }


    override fun getItemCount(): Int {
        return cursor.count
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener?.onClick(v)
    }

    fun getMedicineID(position: Int) : Int{
        return when(cursor.moveToPosition(position)){
            true -> cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            else -> -1
        }
    }

}
