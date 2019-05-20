package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.JoinTomasDelDia
import elements.Toma

@Dao
interface TomaDao {
    @Insert
    fun insert(toma : Toma)

    @Update
    fun update(toma : Toma)

    @Delete
    fun delete(toma : Toma)

    @Query("DELETE FROM Toma")
    fun deleteAllTomas()

    @Query("DELETE FROM Toma WHERE Toma.tratamientoID = :id")
    fun deleteAllTomasTratamiento(id : Int?)

    @Query("SELECT * FROM Toma")
    fun getAllTomas() : LiveData<List<Toma>>

    @Query("SELECT * FROM Toma WHERE Toma.id = :id")
    fun getToma(id : Int?) : LiveData<Toma>

    @Query("SELECT * FROM Toma WHERE Toma.tratamientoID = :id")
    fun getTomasTratamiento(id: Int?) : LiveData<List<Toma>>

    @Query("SELECT statusToma, horaToma, nombreMedicamento, tipo, titulo, color FROM TOMA JOIN Tratamiento ON Tratamiento.id = Toma.tratamientoID JOIN Medicamento ON Tratamiento.medicamentoID = Medicamento.id WHERE Tratamiento.usuarioID = :id")
    fun getTomasDia(id: Int?) :  LiveData<List<JoinTomasDelDia>>
}