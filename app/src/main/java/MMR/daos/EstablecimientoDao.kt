package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.Establecimiento

@Dao
interface EstablecimientoDao {
    @Insert
    fun insert(establecimiento: Establecimiento)

    @Update
    fun update(establecimiento: Establecimiento)

    @Delete
    fun delete(establecimiento: Establecimiento)

    @Query("DELETE FROM Establecimiento")
    fun deleteAllEstablecimientos()

    @Query("SELECT * FROM Establecimiento")
    fun getAllEstablecimientos() : LiveData<List<Establecimiento>>

    @Query("SELECT * FROM Establecimiento WHERE Establecimiento.id = :id" )
    fun getEstablecimiento(id : Int?) : LiveData<Establecimiento>
}