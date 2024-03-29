package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.Usuario

@Dao
interface UsuarioDao {
    @Insert
    fun insert( usuario : Usuario)

    @Update
    fun update( usuario : Usuario)

    @Delete
    fun delete( usuario : Usuario)

    @Query("DELETE FROM Usuario")
    fun deleteAllUsuarios()

    @Query("SELECT * FROM Usuario")
    fun getAllUsuarios() : LiveData<List<Usuario>>

    @Query("SELECT * FROM Usuario WHERE Usuario.uid = :id")
    fun getUsuario( id : Int?) : LiveData<Usuario>

    @Query("SELECT max(uid) FROM Usuario")
    fun getLastUserID() : LiveData<Long>
}