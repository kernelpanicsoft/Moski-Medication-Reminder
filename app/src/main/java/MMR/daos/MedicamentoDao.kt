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
}