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
data class Medicamento (@PrimaryKey(autoGenerate = true) var id: Int,
                   var nombreMedicamento: String? = null,
                   var nombreGenerico: String? = null,
                   var dosis: String? = null,
                   var nota: String? = null,
                   var tipo: String? = null,
                   var color: Int? = null,
                   var fotografia: String? = null,
                   var usuarioID : Int? = null)