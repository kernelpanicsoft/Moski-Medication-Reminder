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
class CitaMedica(@PrimaryKey(autoGenerate = true) var id: Int,
                 var titulo: String? = null,
                 var doctor: String? = null,
                 var especialidad: String? = null,
                 var nota: String? = null,
                 var fecha: String? = null,
                 var hora: String? = null,
                 var ubicacion: String? = null,
                 var tipoRecordatorio: Int? = null,
                 var color: Int? = null,
                 var latitud : Double? = null,
                 var longitud : Double? = null,
                 var statusCita : Int? = null,
                 var usuarioID : Int? = null)