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
    const val SELECCIONAR_IMAGEN = 1004


}

object MMDContract {

    object columnas : BaseColumns {

        const val TABLA_DOCTOR = "doctor"

        const val TITULO_FICHA_CONTACTO = "titulo_ficha_contacto"

    }

}
