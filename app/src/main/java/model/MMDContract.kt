package model

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by spart on 11/03/2018.
 *
 */

object TipoRecordatorio{
    const val NOTIFICACION = 0
    const val ALARMA = 1
    const val NINGUNO = 2
}

object ContinuidadTratamiento{
    const val PERIODO = 0
    const val INDEFINIDO = 1
}


object CodigosDeSolicitud{
    const val ANADIR_FOTOGRAFIA = 1000
    const val REGISTRAR_USUARIO = 1001
    const val EDITAR_USUARIO = 1002
    const val ELIMINAR_USUARIO = 1003

}

object MMDContract {

    object columnas : BaseColumns {

        const val AUTHORITY  = "com.kps.spart.moskimedicationreminder"
        const val SECTION = "ListaFarmacias"

        val CONTENT_BASE_URI : Uri = Uri.parse("content://"  + AUTHORITY + "/" + SECTION)

        const val TABLA_DOCTOR = "doctor"
        const val TITULO_DOCTOR = "titulo_doctor"
        const val NOMBRE_DOCTOR = "nombre_doctor"
        const val ESPECIALIDAD_DOCTOR = "especialidad_doctor"
        const val COLOR_DOCTOR = "color_doctor"
        const val USUARIO_DOCTOR_ID = "usuario_doctor_id"

        const val TABLA_FICHA_CONTACTO = "ficha_contacto"
        const val TITULO_FICHA_CONTACTO = "titulo_ficha_contacto"
        const val DIRECCION_FICHA_CONTACTO = "direccion_ficha_contacto"
        const val TELEFONO_FICHA_CONTACTO = "telefono_ficha_contacto"
        const val CELULAR_FICHA_CONTACTO = "celular_ficha_contacto"
        const val EMAIL_FICHA_CONTACTO = "email_ficha_contacto"
        const val WEB_FICHA_CONTACTO = "web_ficha_contacto"
        const val ACCESSO_FICHA_CONTACTO = "accesso_rapido_ficha_contacto"
        const val LATITUD_FICHA_CONTACTO = "latitud_ficha_contacto"
        const val LONGITUD_FICHA_CONTACTO = "longitud_ficha_contacto"
        const val DOCTOR_FICHA_CONTACTO_ID = "doctor_ficha_contacto_id"

    }

}
