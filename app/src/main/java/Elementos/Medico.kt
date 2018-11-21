package Elementos

import android.content.ContentValues
import model.MMDContract

/**
 * Created by spart on 04/11/2017.
 */

class Medico {
    var nombre: String? = null
    var especialidad: String? = null
    var colorIcono: String? = null
    var icono: String? = null
    var titulo: String? = null

    init {
        nombre = "Luis Gerando Mendez"
        especialidad = "Cardiologo"
        icono = "Doctor"
        colorIcono = "#027569"
        titulo = "Dr."

    }

    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()

        contentValues.put(MMDContract.columnas.TITULO_DOCTOR, titulo)
        contentValues.put(MMDContract.columnas.NOMBRE_DOCTOR, nombre)
        contentValues.put(MMDContract.columnas.ESPECIALIDAD_DOCTOR, especialidad)
        contentValues.put(MMDContract.columnas.ICONO_DOCTOR, icono)
        contentValues.put(MMDContract.columnas.COLOR_DOCTOR, colorIcono)



        return contentValues
    }


}
