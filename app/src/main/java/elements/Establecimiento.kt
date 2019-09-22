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
class Establecimiento(@PrimaryKey(autoGenerate = true) var id : Int,
                      var nombre: String? = null,
                      var tipo: String? = null,
                      var direccion: String? = null,
                      var telefono1: String? = null,
                      var telefono2: String? = null,
                      var email: String? = null,
                      var sitioWeb : String? = null,
                      var latitud : Double? = null,
                      var longitud : Double? = null,
                      var usuarioID : Int? = null
)
