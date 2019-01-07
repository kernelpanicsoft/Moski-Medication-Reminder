package elements

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.ContentValues
import model.MMDContract

@Entity
class Usuario(@PrimaryKey(autoGenerate = true) var uid: Int,
              var nombre: String?,
              var apellidos: String?,
              var edad: Int? = null,
              var genero: String? = null,
              var imagen: String? = null,
              var password: String? = null,
              var email_recuperacion: String? = null
              )

