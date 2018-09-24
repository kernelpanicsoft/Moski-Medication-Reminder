package Elementos

import android.content.ContentValues
import model.MMDContract

class Usuario{


    var nombre: String? = null
    var apellidos: String? = null
    var edad: Int? = null
    var genero: String? = null
    var imagen: String? = null
    var password: String? = null
    var email_recuperacion: String? = null



    /*
    init {
        nombre = ""
        apellidos = ""
        edad = 0
        imagen = ""
        password = ""
        email_recuperacion = ""

    }
*/

    fun printUsuario() : String{
        return nombre + " " + apellidos + " " + edad.toString() + " " + " " + genero + " " + password + " " + email_recuperacion + " " + imagen
    }


    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()

        contentValues.put(MMDContract.columnas.NOMBRE_USUARIO, nombre)
        contentValues.put(MMDContract.columnas.APELLIDOS_USUARIO,apellidos)
        contentValues.put(MMDContract.columnas.EDAD_USUARIO,edad)
        contentValues.put(MMDContract.columnas.GENERO_USUARIO,genero)
        contentValues.put(MMDContract.columnas.PASSWORD_USUARIO,password)
        contentValues.put(MMDContract.columnas.EMAIL_RECUPERACION,email_recuperacion)
        contentValues.put(MMDContract.columnas.IMAGEN_USUARIO,imagen)

        return contentValues
    }

}