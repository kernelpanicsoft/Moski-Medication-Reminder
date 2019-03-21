package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.Medico

@Dao
interface MedicoDao {
    @Insert
    fun insert(medico : Medico)

    @Update
    fun update(medico : Medico)

    @Delete
    fun delete(medico : Medico)

    @Query("DELETE FROM Medico")
    fun deleteAllMedicos()

    @Query("SELECT * FROM Medico")
    fun getAllMedicos() : LiveData<List<Medico>>

    @Query("SELECT * FROM Medico WHERE Medico.id = :id")
    fun getMedico( id : Int? ) : LiveData<Medico>

    @Query("SELECT * FROM Medico WHERE Medico.usuarioID = :id")
    fun getUserMedicos( id : Int? ) : LiveData<List<Medico>>
}