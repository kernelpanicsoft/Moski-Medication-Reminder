package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.JoinMedicamentoTratamientoData
import elements.Tratamiento
import elements.Medicamento

@Dao
interface TratamientoDao {
    @Insert
    fun insert(tratamiento : Tratamiento): Long

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

    @Query("SELECT max(id) FROM Tratamiento")
    fun getLastID() : LiveData<Long>

    @Query("SELECT titulo, nombreMedicamento FROM Medicamento JOIN Tratamiento WHERE Tratamiento.usuarioID = :usuarioID")
    fun getTratamientosConMedicamentoUsuario(usuarioID: Int) : LiveData<List<JoinMedicamentoTratamientoData>>
}