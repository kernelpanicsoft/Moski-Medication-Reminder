package com.kps.spart.moskimedicationreminder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.text.SimpleDateFormat

import android.database.Cursor
import android.provider.BaseColumns
import model.MMDContract

/**
 * Created by spart on 15/12/2017.
 */

class CitasAdapter(private val cursor: Cursor) : RecyclerView.Adapter<CitasAdapter.ViewHolder>(), View.OnClickListener{
    private val sdf = SimpleDateFormat("dd/MM/yyyy, h:mm a")
    var listener: View.OnClickListener? = null


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val esfera: ImageView = v.findViewById(R.id.imageView4)
        val iconoUbicacion: ImageView = v.findViewById(R.id.imageView5)
        val iconoDoctor: ImageView = v.findViewById(R.id.imageView6)
        val iconoFecha: ImageView = v.findViewById(R.id.imageView7)
        val TituloTV: TextView = v.findViewById(R.id.textView4)
        val DoctorTV: TextView = v.findViewById(R.id.medictoCitaTV)
        val DireccionTV: TextView =  v.findViewById(R.id.direccionCitaTV)
        val FechaTV: TextView = v.findViewById(R.id.fechaCitaTV)

    }


    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): CitasAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cita_medica, parent, false)
        v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
       // holder.esfera.setColorFilter(Color.parseColor(items[position].color))
       // holder.iconoFecha.setColorFilter(Color.parseColor(items[position].color))
      //  holder.iconoDoctor.setColorFilter(Color.parseColor(items[position].color))
      //  holder.iconoUbicacion.setColorFilter(Color.parseColor(items[position].color))
        holder.esfera.setColorFilter(cursor.getInt(cursor.getColumnIndexOrThrow(MMDContract.columnas.COLOR_DISTINTIVO_CITA)))
        holder.TituloTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TITULO_CITA))
        holder.DoctorTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.DOCTOR_CITA))
        holder.DireccionTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.UBICACION_CITA))
        //        holder.FechaTV.text = sdf.format(items[position].fechaYhora)

    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun setOnItemClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View){
        listener?.onClick(v)
    }

    fun getAppointmentID(position: Int) : Int{
        return when(cursor.moveToPosition(position)){
            true -> cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            else -> -1
        }
    }


}
