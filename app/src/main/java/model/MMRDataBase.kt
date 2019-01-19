package model


import MMR.daos.EstablecimientoDao
import MMR.daos.MedicamentoDao
import MMR.daos.MedicoDao
import MMR.daos.UsuarioDao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import elements.Establecimiento
import elements.Medicamento
import elements.Medico
import elements.Usuario

@Database(entities = arrayOf(Usuario::class, Medicamento::class, Medico::class, Establecimiento::class), version = 1)
abstract  class MMRDataBase : RoomDatabase() {


    abstract fun usuarioDao(): UsuarioDao
    abstract fun medicamentoDao() : MedicamentoDao
    abstract fun medicoDao() : MedicoDao
    abstract fun establecimientoDao() : EstablecimientoDao

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