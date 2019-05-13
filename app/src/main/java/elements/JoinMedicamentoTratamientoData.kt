package elements

import android.arch.persistence.room.ColumnInfo

data class JoinMedicamentoTratamientoData(
        @ColumnInfo(name ="titulo")  val titulo: String?,
        @ColumnInfo(name ="nombreMedicamento") val medicamento: String?

)
//{
   // var tituloTratamiento : String? = null
   // var medicamento : String? = null
   // var colorMedicamento : Int? = null

//
// }