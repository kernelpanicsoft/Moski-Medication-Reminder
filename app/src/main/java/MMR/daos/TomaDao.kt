package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.JoinTomasDelDia
import elements.Toma
import model.EstatusToma
import model.TipoRecordatorio


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

    @Query("SELECT Toma.id, statusToma, horaToma, nombreMedicamento, tipo, titulo, color, Tratamiento.id AS tomaID FROM TOMA JOIN Tratamiento ON Tratamiento.id = Toma.tratamientoID JOIN Medicamento ON Tratamiento.medicamentoID = Medicamento.id WHERE Tratamiento.usuarioID = :id")
    fun getTomasDia(id: Int?) :  LiveData<List<JoinTomasDelDia>>

    @Query("UPDATE Toma SET statusToma=:status WHERE id = :id")
    fun updateTomaStatus(id: Int, status: Int)

    @Query("UPDATE Toma SET statusToma = " + EstatusToma.PROGRAMADA)
    fun resetTomasStatus()

    @Query("SELECT Toma.id, statusToma, horaToma, nombreMedicamento FROM Toma JOIN Tratamiento ON Tratamiento.id = Toma.tratamientoID JOIN Medicamento ON Tratamiento.medicamentoID = Medicamento.id WHERE Tratamiento.recordatorio = " + TipoRecordatorio.NOTIFICACION)
    fun getTomasProgramadas() : LiveData<JoinTomasDelDia>

    @Query("SELECT Toma.id, statusToma, horaToma, titulo, nombreMedicamento, recordatorio FROM Toma JOIN Tratamiento ON Tratamiento.id = Toma.tratamientoID JOIN Medicamento ON Tratamiento.medicamentoID = Medicamento.id")
    fun getTomasProgramadasWithoutLiveData() : List<JoinTomasDelDia>




}