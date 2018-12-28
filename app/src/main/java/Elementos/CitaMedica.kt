package Elementos

import android.content.ContentValues
import model.MMDContract
import java.util.Calendar
import java.util.Date

/**
 * Created by spart on 12/12/2017.
 */

class CitaMedica {
    var titulo: String? = null
    var doctor: String? = null
    var especialidad: String? = null
    var nota: String? = null
    var fechaYhora: String? = null
    var ubicacion: String? = null
    var tipoRecordatorio: Int? = null
    var color: String? = null
    var latitud : Double? = null
    var longitud : Double? = null
    var usuarioID : Int? = null


    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()
        contentValues.put(MMDContract.columnas.TITULO_CITA, titulo)
        contentValues.put(MMDContract.columnas.DOCTOR_CITA, doctor)
        contentValues.put(MMDContract.columnas.ESPECIALIDAD_DOCTOR_CITA, especialidad)
        contentValues.put(MMDContract.columnas.NOTA_CITA,nota)
        contentValues.put(MMDContract.columnas.FECHA_HORA_CITA, fechaYhora)
        contentValues.put(MMDContract.columnas.UBICACION_CITA, ubicacion)
        contentValues.put(MMDContract.columnas.TIPO_RECORDATORIO_CITA, tipoRecordatorio)
        contentValues.put(MMDContract.columnas.COLOR_DISTINTIVO_CITA, color)
        contentValues.put(MMDContract.columnas.LATITUD_CITA, latitud)
        contentValues.put(MMDContract.columnas.LONGITUD_CITA, longitud)
        contentValues.put(MMDContract.columnas.USUARIO_CITA_ID, usuarioID)

        return contentValues
    }
}
