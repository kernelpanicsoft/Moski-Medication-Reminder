package elements

import android.arch.persistence.room.ColumnInfo

data class JoinTomasDelDia(
        @ColumnInfo(name = "id") val id: Int?,
        @ColumnInfo(name = "horaToma") val horaToma: String?,
        @ColumnInfo(name = "statusToma") var statusToma: Int?,
        @ColumnInfo(name = "nombreMedicamento") val medicamento: String?,
        @ColumnInfo(name = "tipo") val tipo: String?,
        @ColumnInfo(name = "titulo") var tituloTratamiento: String?,
        @ColumnInfo(name = "color") val color: Int?,
        @ColumnInfo(name = "tomaID") val idTratamiento: Int?,
        @ColumnInfo(name = "recordatorio") val tipoRecordatorio: Int?
)