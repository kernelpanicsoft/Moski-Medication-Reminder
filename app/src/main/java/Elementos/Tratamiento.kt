package Elementos

import java.util.ArrayList
import java.util.Date

/**
 * Created by spart on 19/12/2017.
 */

class Tratamiento {
    var nombreTratamiento: String? = null
    var medicamento: String? = null
    var iconoMedicamento: String? = null
    var indicaciones: String? = null
    var fechaInicio: String? = null
    var fechaFin: String? = null
    var status: String? = null
    var diasTratamiento: Int = 0
    var horaInicio: String? = null
    var intervaloHoras: String? = null
    private var listaHorario: ArrayList<Date>? = null

    init {
        nombreTratamiento = "Extraccion muela del juicio"
        medicamento = "Paracetamol"
        iconoMedicamento = "capsula"
        indicaciones = "Tomar despues de haber comido"
        fechaFin = ""
        fechaFin = ""
        status = "activo"
        diasTratamiento = 24
        horaInicio = "12:45"
        intervaloHoras = "30"

    }


}
