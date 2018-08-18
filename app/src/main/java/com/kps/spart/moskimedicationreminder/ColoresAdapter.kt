package com.kps.spart.moskimedicationreminder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

/**
 * Created by spart on 01/12/2017.
 */
class ColoresAdapter internal constructor(private val items: Array<String>) : RecyclerView.Adapter<ColoresAdapter.ViewHolder>(), View.OnClickListener {
    private val listener: View.OnClickListener? = null

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icono: ImageView

        init {

            icono = v.findViewById<View>(R.id.colorIconoIV) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColoresAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_color, parent, false)

        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icono.setColorFilter(Color.parseColor(items[position]))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onClick(v: View) {
        listener?.onClick(v)
    }
}
