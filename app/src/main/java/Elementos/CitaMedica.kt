package Elementos

import java.util.Calendar
import java.util.Date

/**
 * Created by spart on 12/12/2017.
 */

class CitaMedica {
    var titulo: String? = null
    var medico: String? = null
    var especialidad: String? = null
    var fechaYhora: Date? = null
    var ubicacion: String? = null
    var color: String? = null
    var nota: String? = null

    init {
        titulo = "limpieza dental"
        medico = "Alma Delia"
        especialidad = "Otorrinolaringologia"
        fechaYhora = Calendar.getInstance().time
        ubicacion = "42Poniente, Colonia Volcanes, Puebla, Puebla"
        color = "#b200ff"
        nota = "Acudir con los dientes limpios y haber comido al menos media hora antes"
    }


}
