package model

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by spart on 11/03/2018.
 *
 */

object MMDContract {

    object columnas : BaseColumns {

        const val AUTHORITY  = "com.kps.spart.moskimedicationreminder"
        const val SECTION = "ListaFarmacias"

        val CONTENT_BASE_URI : Uri = Uri.parse("content://"  + AUTHORITY + "/" + SECTION)

        const val TABLA_DOCTOR = "doctor"
        const val TITULO_DOCTOR = "titulo_doctor"
        const val NOMBRE_DOCTOR = "nombre_doctor"
        const val ESPECIALIDAD_DOCTOR = "especialidad_doctor"
        const val ICONO_DOCTOR = "icono_doctor"
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


        const val TABLA_CITA = "citas"
        const val TITULO_CITA = "titulo_cita"
        const val DIRECCION_CITA = "direccion_cita"
        const val FECHA_HORA_CITA = "fecha_hora_cita"
        const val NOTA_CITA = "nota_cita"
        const val LATITUD_CITA = "latitud_cita"
        const val LONGITUD_CITA = "longitud_cita"
        const val COLOR_DISTINTIVO_CITA = "color_distintivo_cita"
        const val DOCTOR_CITA_ID = "doctor_cita_id"
        const val USUARIO_CITA_ID = "usuario_cita_id"

        const val TABLA_USUARIO = "usuario"
        const val NOMBRE_USUARIO = "nombre_usuario"
        const val APELLIDOS_USUARIO = "apellidos_usuarios"
        const val EDAD_USUARIO = "edad_usuario"
        const val GENERO_USUARIO = "genero_usuario"
        const val PASSWORD_USUARIO = "password_usuario"
        const val IMAGEN_USUARIO = "imagen_usuario"
        const val EMAIL_RECUPERACION = "email_recuperacion_usuario"

        const val TABLA_ESTABLECIMIENTO = "establecimiento"
        const val NOMBRE_ESTABLECIMIENTO = "nombre_establecimiento"
        const val TIPO_ESTABLECIMIENTO = "tipo_establecimiento"
        const val DIRECCION_ESTABLECIMIENTO = "direccion_establecimiento"
        const val TELEFONO1_ESTABLECIMIENTO = "telefono1_establecimiento"
        const val TELEFONO2_ESTABLECIMIENTO = "telefono2_establecimiento"
        const val EMAIL_ESTABLECIMIENTO = "email_establecimiento"
        const val WEB_ESTABLECIMIENTO = "web_establecimiento"
        const val LATITUD_ESTABLECIMIENTO = "latitud_establecimiento"
        const val LONGITUD_ESTABLECIMIENTO = "longitud_establecimiento"
        const val USUARIO_ESTABLECIMIENTO_ID = "usuario_establecimiento_id"

        const val TABLA_MEDICAMENTO = "medicamento"
        const val NOMBRE_COMERCIAL_MEDICAMENTO = "nombre_comercial_medicamento"
        const val NOMBRE_GENERICO_MEDICAMENTO = "nombre_generico_medicamento"
        const val DOSIS_MEDICAMENTO = "dosis_medicamento"
        const val COLOR_MEDICAMENTO = "color_distintivo_medicamento"
        const val TIPO_MEDICAMENTO = "tipo_medicamento"
        const val FOTOGRAFIA_MEDICAMENTO = "fotografia_medicamento"
        const val NOTA_MEDICAMENTO = "nota_medicamento"
        const val USUARIO_MEDICAMENTO_ID = "usuario_medicamento_id"

        const val TABLA_TRATAMIENTO = "tratamiento"
        const val TITULO_TRATAMINETO = "titulo_tratamiento"
        const val FECHA_INICIO_TRATAMIENTO = "fecha_inicio_tratamiento"
        const val FECHA_FIN_TRATAMIENTO = "fecha_fin_tratamiento"
        const val DIAS_TRATAMIENTO = "dias_tratamiento"
        const val STATUS_TRATAMIENTO = "status_tratamiento"
        const val MEDICAMENTO_TRATAMIENTO_ID = "medicamento_tratamiento_id"
        const val USUARIO_TRATAMIENTO_ID = "usuario_tratamiento_id"

        const val TABLA_TOMA = "toma"
        const val HORA_TOMA = "hora_toma"
        const val STATUS_TOMA = "status_toma"
        const val TRATAMIENTO_TOMA_ID = "tratamiento_toma_id"

    }

}
