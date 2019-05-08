package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TomaViewModel
import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.recyclerview.extensions.ListAdapter
import android.widget.ImageView
import android.widget.TextView
import elements.Toma
import kotlinx.android.synthetic.main.activity_anadir_tomas.*


class RegistrarTomasAdapter(val tomasViewModel: TomaViewModel) : ListAdapter<Toma, RegistrarTomasAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Toma>(){
        override fun areItemsTheSame(oldItem: Toma, newItem: Toma): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Toma, newItem: Toma): Boolean {
            return oldItem.horaToma.equals(newItem.horaToma)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val horaToma = v.findViewById<TextView>(R.id.horaTomaTV)
        val iconoEliminar = v.findViewById<ImageView>(R.id.deleteShootIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RegistrarTomasAdapter.ViewHolder{
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_toma_anadida, parent, false)
        v.setOnClickListener(this)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val tomaActual = getItem(position)
        holder.horaToma.text = tomaActual.horaToma
        holder.iconoEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle(it.context.getString(R.string.eliminar_toma))
                    .setPositiveButton(it.context.getString(R.string.eliminar)){
                        dialog, id ->
                        //tomasViewModel.delete(adapter.getShootAt(RecViewAddTomas.getChildAdapterPosition(it)))
                        tomasViewModel.delete(getItem(position))

                    }
                    .setNegativeButton(it.context.getString(R.string.cancelar)){
                        dialog, id ->
                    }

            val dialog = builder.create()
            dialog.show()
        }

    }

    fun setOnItemClickListener(listener: View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }

    fun getShootAt(position: Int) : Toma{
        return getItem(position)
    }
}