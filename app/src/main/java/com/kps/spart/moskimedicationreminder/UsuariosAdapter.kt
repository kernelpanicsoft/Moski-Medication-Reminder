package com.kps.spart.moskimedicationreminder


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.support.v7.recyclerview.extensions.ListAdapter
import android.widget.TextView
import elements.Usuario
import java.io.FileNotFoundException


class UsuariosAdapter: ListAdapter<Usuario,UsuariosAdapter.ViewHolder>(DIFF_CALLBACK()), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    class DIFF_CALLBACK : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.nombre.equals(newItem.nombre)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val icono : ImageView = v.findViewById(R.id.UsuarioIV)
        val nombre : TextView = v.findViewById(R.id.NombreUsuarioTV) as TextView
        val apellidos : TextView = v.findViewById(R.id.ApellidosUsuarioTV) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_usuario,parent,false)
        v.setOnClickListener(this)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuarioActual = getItem(position)


        if(usuarioActual.imagen.isNullOrEmpty()){
            holder.icono.setImageResource(R.drawable.ic_user)
        }else{
            val  valueInPixels = holder.icono.context.resources.getDimension(R.dimen.listItemImagen)
            holder.icono.setImageBitmap(setPic(usuarioActual.imagen!!,valueInPixels.toInt(),valueInPixels.toInt()))
        }

        holder.nombre.text = usuarioActual.nombre
        holder.apellidos.text = usuarioActual.apellidos
    }

    fun setOnClickListener(listener : View.OnClickListener){
        this.listener = listener
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }

    fun getUsuarioAt(position : Int) : Usuario{
        return getItem(position)
    }

    private fun setPic(mCurrentPhotoPath : String, targetW : Int, targetH : Int) : Bitmap? {

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(mCurrentPhotoPath,this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor : Int = Math.min(photoW / targetW , photoH / targetH )

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }

        var scaledBitmap : Bitmap? = null
        try {

        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)?.also { bitmap ->
            scaledBitmap = bitmap
        }

        }catch ( e : FileNotFoundException){
            return null
        }
        return scaledBitmap
    }
}
