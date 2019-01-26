package MMR.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import elements.VentaMedicamento

@Dao
interface VentaMedicamentoDao {
    @Insert
    fun insert(ventaMedicamento : VentaMedicamento)

    @Update
    fun update(ventaMedicamento: VentaMedicamento)

    @Delete
    fun delete(ventaMedicamento: VentaMedicamento)

    @Query("SELECT * FROM VentaMedicamento")
    fun getAllVentasMedicamento() : LiveData<List<VentaMedicamento>>

    @Query("SELECT * FROM VentaMedicamento WHERE VentaMedicamento.medicamentoId = :id")
    fun getVentaMedicamentoByMedicamento( id : Int) : LiveData<List<VentaMedicamento>>

    @Query("SELECT * FROM VentaMedicamento WHERE VentaMedicamento.establecimientoId = :id")
    fun getVentaMedicamentoByEstablecimiento ( id : Int) : LiveData<List<VentaMedicamento>>

}