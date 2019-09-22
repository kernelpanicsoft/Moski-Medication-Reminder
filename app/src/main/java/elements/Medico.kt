package elements

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.content.ContentValues
import model.MMDContract



@Entity(foreignKeys = arrayOf(ForeignKey(entity = Usuario::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("usuarioID"),
        onDelete = ForeignKey.CASCADE)))
class Medico (@PrimaryKey(autoGenerate = true) var id: Int,
              var nombre: String? = null,
              var especialidad: String? = null,
              var titulo: String? = null,
              var fichaContactoAR: Int = -1,
              var usuarioID : Int? = null
              )
