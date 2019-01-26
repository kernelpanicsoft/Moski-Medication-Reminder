package elements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Medicamento::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("medicamentoId"),
                onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(entity = Establecimiento::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("establecimientoId"),
                onDelete = ForeignKey.CASCADE
                )
))
class VentaMedicamento(@PrimaryKey(autoGenerate = true) var id: Int,
                       var medicamentoId : Int? = null,
                       var establecimientoId : Int? = null
                       )

