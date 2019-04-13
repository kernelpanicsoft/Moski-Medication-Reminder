package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.Tratamiento

@Dao
interface TratamientoDao {
    @Insert
    fun insert(tratamiento : Tratamiento)

    @Update
    fun update(tratamiento: Tratamiento)

    @Delete
    fun delete(tratamiento: Tratamiento)

    @Query("DELETE FROM Tratamiento")
    fun deleteAllTratamientos()

    @Query("DELETE FROM Tratamiento WHERE Tratamiento.usuarioID = :id")
    fun deleteAllTratamientosUsuario(id: Int?)

    @Query("SELECT * FROM Tratamiento WHERE Tratamiento.usuarioID = :usuarioID")
    fun getTratamientosUsuario(usuarioID : Int) : LiveData<List<Tratamiento>>

    @Query("SELECT * FROM Tratamiento WHERE Tratamiento.id = :id")
    fun getTratamiento(id : Int?) : LiveData<Tratamiento>

}