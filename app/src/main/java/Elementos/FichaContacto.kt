package Elementos

import android.content.ContentValues
import model.MMDContract

class FichaContacto (var titulo : String? = null, var direccion : String = "", var telefono : String = "", var celular : String = "", var email : String = "",  var sitioweb : String = "", var accesoRapido : Boolean = false, var medico_ficha_id : Int = 0){

    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()

        contentValues.put(MMDContract.columnas.TITULO_FICHA_CONTACTO, titulo)
        contentValues.put(MMDContract.columnas.DIRECCION_FICHA_CONTACTO, direccion)
        contentValues.put(MMDContract.columnas.TELEFONO_FICHA_CONTACTO, telefono)
        contentValues.put(MMDContract.columnas.CELULAR_FICHA_CONTACTO, celular)
        contentValues.put(MMDContract.columnas.EMAIL_FICHA_CONTACTO, email)
        contentValues.put(MMDContract.columnas.WEB_FICHA_CONTACTO, sitioweb)
        if(accesoRapido){
            contentValues.put(MMDContract.columnas.ACCESSO_FICHA_CONTACTO, 1)
        }else{
            contentValues.put(MMDContract.columnas.ACCESSO_FICHA_CONTACTO, 0)
        }
        contentValues.put(MMDContract.columnas.DOCTOR_FICHA_CONTACTO_ID, medico_ficha_id)


        return contentValues
    }

    override fun toString() : String{
        return "$titulo $direccion $telefono $celular $email $sitioweb $$accesoRapido $medico_ficha_id"
    }


}