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
    fun getCitaMedica(id : Int?) : LiveData<CitaMedica>

    @Query("SELECT * FROM CitaMedica WHERE CitaMedica.usuarioID = :id")
    fun getCitasUsuario(id : Int?) : LiveData<List<CitaMedica>>

    @Query("SELECT id, titulo, doctor,especialidad, fecha, hora, tipoRecordatorio FROM CitaMedica WHERE fecha = date('now')")
    fun getCitasProgramadasWithoutLiveData() : List<CitaMedica>

    @Query("SELECT COUNT(*) FROM CitaMedica WHERE CitaMedica.usuarioID = :id")
    fun getCitasCount( id : Int? ) : LiveData<Int>
}