package elements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey


@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Tratamiento::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("tratamientoID"),
                onDelete = ForeignKey.CASCADE
                )
))
class Toma(@PrimaryKey(autoGenerate = true) var id: Int,
    var statusToma : Int = 0,
    var horaToma: String? = null,
    var tratamientoID: Int? = null

    )

