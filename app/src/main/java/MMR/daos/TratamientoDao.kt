package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.JoinMedicamentoTratamientoData
import elements.Tratamiento
import elements.Medicamento
import model.EstatusTratamiento

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

    @Query("SELECT titulo, nombreMedicamento, color, tipo, Tratamiento.id, status FROM Medicamento JOIN Tratamiento ON Tratamiento.medicamentoID = Medicamento.id WHERE Tratamiento.usuarioID = :usuarioID")
    fun getTratamientosConMedicamentoUsuario(usuarioID: Int) : LiveData<List<JoinMedicamentoTratamientoData>>

    @Query("UPDATE Tratamiento SET atiempo = atiempo + 1 WHERE Tratamiento.id = :id")
    fun incrementTomasATiempo(id: Int)

    @Query("UPDATE Tratamiento SET pospuestas = pospuestas + 1 WHERE Tratamiento.id = :id")
    fun incrementTomasPospuestas(id: Int)

    @Query("UPDATE Tratamiento SET omitidas = omitidas + 1 WHERE Tratamiento.id = :id")
    fun incrementTomasOmitidas(id: Int)

    @Query("UPDATE Tratamiento SET status = " + EstatusTratamiento.TERMINADO +  "  WHERE Tratamiento.diasTratamiento = 0")
    fun endsTreatments()

    @Query("UPDATE Tratamiento SET diasTratamiento = diasTratamiento - 1 WHERE diasTratamiento != -1")
    fun decreaseTreatmentDays()

}