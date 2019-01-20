package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.FichaContacto

@Dao
interface FichaContactoDao {
    @Insert
    fun insert(fichaContacto: FichaContacto)

    @Update
    fun update(fichaContacto: FichaContacto)

    @Delete
    fun delete(fichaContacto: FichaContacto)

    @Query("DELETE FROM FichaContacto WHERE FichaContacto.medico_ficha_id = :id")
    fun deleteAllFichasContacto(id : Int?)

    @Query("SELECT * FROM FichaContacto WHERE FichaContacto.medico_ficha_id = :id")
    fun getFichasContactoDeMedico(id : Int?) : LiveData<List<FichaContacto>>

    @Query("SELECT * FROM FichaContacto WHERE FichaContacto.id = :id")
    fun getFichaContacto(id : Int?) : LiveData<FichaContacto>
}