package model


import MMR.daos.*
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import elements.*

@Database(entities = arrayOf(Usuario::class,
                            Medicamento::class,
                            Medico::class,
                            FichaContacto::class,
                            Establecimiento::class,
                            CitaMedica::class,
                            Tratamiento::class,
                            Toma::class
),
        version = 1)
abstract  class MMRDataBase : RoomDatabase() {


    abstract fun usuarioDao(): UsuarioDao
    abstract fun medicamentoDao() : MedicamentoDao
    abstract fun medicoDao() : MedicoDao
    abstract fun fichaContactoDao() : FichaContactoDao
    abstract fun establecimientoDao() : EstablecimientoDao
    abstract fun citaMedicaDao() : CitaMedicaDao
    abstract fun tratamientoDao() : TratamientoDao
    abstract fun tomaDao() : TomaDao
    companion object {
        @Volatile private var instance : MMRDataBase? = null

        fun getInstance(context: Context) : MMRDataBase =
                instance ?: synchronized(this){
                    instance ?: buildDatabase(context).also { instance = it }
                }

        private fun buildDatabase(context : Context) =
                Room.databaseBuilder(context.applicationContext,
                        MMRDataBase::class.java, "MMRDatabase.db")
                        .build()
    }
}