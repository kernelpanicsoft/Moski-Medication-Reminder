package elements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.content.ContentValues
import model.MMDContract

/**
 * Created by spart on 04/11/2017.
 */

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Usuario::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("usuarioID"),
        onDelete = ForeignKey.CASCADE)))
class Medico (@PrimaryKey(autoGenerate = true) var id: Int,
              var nombre: String? = null,
              var especialidad: String? = null,
              var titulo: String? = null,
              var usuarioID : Int? = null
              )
