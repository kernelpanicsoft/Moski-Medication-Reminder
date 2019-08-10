package model

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by spart on 11/03/2018.
 *
 */

const val REGISTRO_PRIMER_USUARIO = 2001
const val CANAL_PRIMARIO_ID = "primary_notification_channel"
const val NOTIFICACION_ID = 6832
const val ACTION_UPDATE_NOTIFICATION = "com.kps.spart.moskimedicationreminder"



object TipoRecordatorio{
    const val NOTIFICACION = 0
    const val ALARMA = 1
    const val NINGUNO = 2
}

object AccionNotificacion{
    const val TOMAR = 500
    const val SALTAR = 501
    const val POSPONER = 502
    const val PAUSAR_SONIDO = 503
}


object EstatusTratamiento{
    const val ACTIVO = 0
    const val TERMINADO = 1
    const val PAUSADO = 2
    const val PROGRAMADO = 3
}


object EstatusToma{
    const val PROGRAMADA = 1
    const val TOMADA = 2
    const val PASADA = 3
    const val POSPUESTA = 4
}


object CodigosDeSolicitud{
    const val ANADIR_FOTOGRAFIA = 1000
    const val REGISTRAR_USUARIO = 1001
    const val EDITAR_USUARIO = 1002
    const val ELIMINAR_USUARIO = 1003
    const val SELECCIONAR_IMAGEN = 1004
    const val SOLICITAR_PERMISO_ALMACENAMIENTO_EXTERNO = 1005
    const val EDITAR_MEDICAMENTO = 1006
    const val ELIMINAR_MEDICAMENTO = 1007
    const val EDITAR_ESTABLECIMIENTO = 1008
    const val ELIMINAR_ESTABLECIMIENTO = 1009
    const val EDITAR_CITA = 1010
    const val ELIMINAR_CITA = 1011
    const val ELEGIR_MEDICAMENTO = 1012
    const val ANADIR_TRATAMIENTO = 1013
    const val ANADIR_MEDICO = 1014
    const val REGISTRAR_PRIMER_USUARIO = 1015
    const val ANADIR_CITA_MEDICA = 1016
    const val ANADIR_TOMAS = 1017
}

object MMDContract {

    object columnas : BaseColumns {

        const val TABLA_DOCTOR = "doctor"

    }

}
