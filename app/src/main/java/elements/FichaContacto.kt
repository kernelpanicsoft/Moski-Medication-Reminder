package elements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Medico::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("medico_ficha_id"),
        onDelete = ForeignKey.CASCADE)))
class FichaContacto (@PrimaryKey(autoGenerate = true) var id: Int,
                     var titulo : String? = null,
                     var direccion : String = "",
                     var telefono : String = "",
                     var celular : String = "",
                     var email : String = "",
                     var sitioweb : String = "",
                     var accesoRapido : Boolean = false,
                     var medico_ficha_id : Int = 0)