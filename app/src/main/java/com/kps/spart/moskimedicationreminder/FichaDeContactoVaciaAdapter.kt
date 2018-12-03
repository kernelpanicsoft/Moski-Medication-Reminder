package com.kps.spart.moskimedicationreminder
import Elementos.FichaContacto
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item_ficha_contacto.view.*


class FichaDeContactoVaciaAdapter(context: Context,var items : List<FichaContacto>) : ArrayAdapter<FichaContacto>(context, 0, items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.list_item_ficha_contacto, parent,false)

        val currentFichaContacto = getItem(position)
        currentFichaContacto.titulo = rowView.textInputLayoutTituloficha.text.toString()
        currentFichaContacto.direccion = rowView.textInputLayoutDireccion.text.toString()
        currentFichaContacto.telefono = rowView.textInputLayoutTelefono.text.toString()

        val addCardButton = rowView.findViewById<Button>(R.id.anadirFichaButton)
        addCardButton.setOnClickListener{
            Toast.makeText(rowView.context,"Estás haciendo click en el label " + position + " Con valores: " + currentFichaContacto.toString(), Toast.LENGTH_SHORT).show()
            add(FichaContacto())
        }





        val removeCardText = rowView.findViewById<TextView>(R.id.removerFicha)

        if (count == 1){
            removeCardText.visibility = View.GONE
        }
        removeCardText.setOnClickListener{

            if(count > 1){
                remove(getItem(position))
            }
        }

        return rowView

    }


}