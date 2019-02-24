package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.Medicamento

@Dao
interface MedicamentoDao {
    @Insert
    fun insert( medicamento : Medicamento)

    @Update
    fun update( medicamento: Medicamento)

    @Delete
    fun delete ( medicamento: Medicamento)

    @Query("DELETE FROM Medicamento")
    fun deleteAllMedicamentos()

    @Query("SELECT * FROM Medicamento")
    fun getAllMedicamentos() : LiveData<List<Medicamento>>

    @Query("SELECT * FROM Medicamento WHERE Medicamento.id = :id")
    fun getMedicamento( id : Int? ) : LiveData<Medicamento>

    @Query("SELECT * FROM Medicamento WHERE Medicamento.usuarioID = :id")
    fun getUserMedicamentos(id : Int?) : LiveData<List<Medicamento>>
}