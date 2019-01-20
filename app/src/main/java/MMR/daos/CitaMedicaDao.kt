package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.CitaMedica

@Dao
interface CitaMedicaDao {
    @Insert
    fun insert(citaMedica : CitaMedica)

    @Update
    fun update(citaMedica: CitaMedica)

    @Delete
    fun delete(citaMedica: CitaMedica)

    @Query("DELETE FROM CitaMedica")
    fun deleteAllCitasMedicas()

    @Query("SELECT * FROM CitaMedica")
    fun getAllCitasMedicas() : LiveData<List<CitaMedica>>

    @Query("SELECT * FROM CitaMedica WHERE CitaMedica.id = :id")
    fun getCitaMEdica(id : Int?) : LiveData<CitaMedica>

}