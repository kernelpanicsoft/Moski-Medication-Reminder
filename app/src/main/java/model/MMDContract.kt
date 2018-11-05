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
        const val NOMBRE_DOCTOR = "nombre_doctor"
        const val ESPECIALIDAD_DOCTOR = "especialidad_doctor"
        const val ICONO_DOCTOR = "icono_doctor"
        const val COLOR_DOCTOR = "color_doctor"
        const val LATITUD_DOCTOR = "latitud_doctor"
        const val LONGITUD_DOCTOR = "longitud_doctor"
        const val TITULO_DOCTOR_ID = "titulo_doctor_id"
        const val USUARIO_DOCTOR_ID = "usuario_doctor_id"

        const val TABLA_TITULO_DOCTOR = "titulo_doctor"
        const val TITULO_DOCTOR = "titulo"

        const val TABLA_EMAIL_DOCTOR = "email_doctor"
        const val EMAIL_DOCTOR = "email"
        const val DOCTOR_EMAIL_ID = "doctor_email_id"

        const val TABLA_TELEFONO_DOCTOR = "telefono_doctor"
        const val TELEFONO_DOCTOR = "telefono"
        const val ICONO_TELEFONO_DOCTOR = "icono"
        const val DOCTOR_TELEFONO_ID = "doctor_telefono_id"

        const val TABLA_DIRECCION_DOCTOR = "direccion_doctor"
        const val DIRECCION__DOCTOR = "direccion"
        const val ICONO_DIRECCION_DOCTOR = "icono"
        const val DOCTOR_DIRECCION_ID = "doctor_direccion_id"

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

        const val TABLA_FARMACIA = "farmacia"
        const val NOMBRE_FARMACIA = "nombre_farmacia"
        const val DIRECCION_FARMACIA = "direccion_farmacia"
        const val TELEFONO1_FARMACIA = "telefono1_farmacia"
        const val TELEFONO2_FARMACIA = "telefono2_farmacia"
        const val EMAIL_FARMACIA = "email_farmacia"
        const val WEB_FARMACIA = "web_farmacia"
        const val LATITUD_FARMACIA = "latitud_farmacia"
        const val LONGITUD_FARMACIA = "longitud_farmacia"
        const val USUARIO_FARMACIA_ID = "usuario_farmacia_id"

        const val TABLA_MEDICAMENTO = "medicamento"
        const val NOMBRE_COMERCIAL_MEDICAMENTO = "nombre_comercial_medicamento"
        const val NOMBRE_GENERICO_MEDICAMENTO = "nombre_generico_medicamento"
        const val DOSIS_MEDICAMENTO = "dosis_medicamento"
        const val FOTOGRAFIA_MEDICAMENTO = "fotografia_medicamento"
        const val ICONO_MEDICAMENTO = "icono_medicamento"
        const val TIPO_MEDICAMENTO = "tipo_medicamento"
        const val COLOR_DISTINTIVO_MEDICAMENTO = "color_distintivo_medicamento"
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
