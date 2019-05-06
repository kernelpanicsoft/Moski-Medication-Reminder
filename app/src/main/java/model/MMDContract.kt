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

object EstatusTratamiento{
    const val ACTIVO = 0
    const val TERMINADO = 1
    const val PAUSADO = 2
}

object EstatusCita{
    const val PENDIENTE = 1
    const val ATENDIDA = 2
    const val PASADA = 3
}

object EstatusToma{
    const val PROGRAMADA = 1
    const val TOMADA = 2
    const val PASADA = 3
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
}

object MMDContract {

    object columnas : BaseColumns {

        const val TABLA_DOCTOR = "doctor"

    }

}
