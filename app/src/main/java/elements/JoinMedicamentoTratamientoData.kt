package elements

import android.arch.persistence.room.ColumnInfo

data class JoinMedicamentoTratamientoData(
        @ColumnInfo(name ="titulo")  val titulo: String?,
        @ColumnInfo(name ="nombreMedicamento") val medicamento: String?,
        @ColumnInfo(name = "color") val color: Int?,
        @ColumnInfo(name = "tipo") val tipo: String?,
        @ColumnInfo(name = "id") val tratamientoID: Int?,
        @ColumnInfo(name = "status") val estatusTratamiento: Int?

)
