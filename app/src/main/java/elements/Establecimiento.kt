package elements

import android.content.ContentValues
import model.MMDContract

/**
 * Created by spart on 03/11/2017.
 */

class Establecimiento {
    var nombre: String? = null
    var tipo: String? = null
    var direccion: String? = null
    var telefono1: String? = null
    var telefono2: String? = null
    var email: String? = null
    var sitioWeb : String? = null
    var latitud : Double? = null
    var longitud : Double? = null
    var usuarioID : Int? = null


    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()

        contentValues.put(MMDContract.columnas.NOMBRE_ESTABLECIMIENTO, nombre)
        contentValues.put(MMDContract.columnas.TIPO_ESTABLECIMIENTO, tipo)
        contentValues.put(MMDContract.columnas.DIRECCION_ESTABLECIMIENTO, direccion)
        contentValues.put(MMDContract.columnas.TELEFONO1_ESTABLECIMIENTO, telefono1)
        contentValues.put(MMDContract.columnas.TELEFONO2_ESTABLECIMIENTO, telefono2)
        contentValues.put(MMDContract.columnas.WEB_ESTABLECIMIENTO, sitioWeb)
        contentValues.put(MMDContract.columnas.EMAIL_ESTABLECIMIENTO, email)
        contentValues.put(MMDContract.columnas.LATITUD_ESTABLECIMIENTO, latitud)
        contentValues.put(MMDContract.columnas.LONGITUD_ESTABLECIMIENTO, longitud)
        contentValues.put(MMDContract.columnas.USUARIO_ESTABLECIMIENTO_ID, usuarioID)
        return contentValues
    }

}