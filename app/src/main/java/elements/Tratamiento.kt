package elements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.ArrayList
import java.util.Date


@Entity(foreignKeys =  arrayOf(
        ForeignKey(entity = Medicamento::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("medicamentoID"),
                    onDelete = ForeignKey.CASCADE),

        ForeignKey(entity = Usuario::class,
                parentColumns = arrayOf("uid"),
                childColumns = arrayOf("usuarioID"),
                onDelete = ForeignKey.CASCADE
                )
))
class Tratamiento (@PrimaryKey(autoGenerate = true) var id: Int,
                   var titulo: String? = null,
                   var usuarioID: Int? = null,
                   var medicamentoID: Int? = null,
                   var indicaciones: String? = null,
                   var fechaInicio: String? = null,
                   var fechaFin: String? = null,
                   var diasTratamiento: Int = 0,
                   var status: Int? = null,
                   var recordatorio: Int? = null,
                   var atiempo: Int = 0,
                   var pospuestas: Int = 0,
                   var omitidas: Int = 0)
