package MMR.viewModels

import MMR.repositories.VentaMedicamentoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.VentaMedicamento

class VentaMedicamentoViewModel(application: Application) : AndroidViewModel(application) {
    val repository : VentaMedicamentoRepository = VentaMedicamentoRepository(application)
    val allVentaMedicamento : LiveData<List<VentaMedicamento>>

    init{
        allVentaMedicamento = repository.getAllVentasMedicamento()
    }

    fun insert(ventaMedicamento: VentaMedicamento){
        repository.insert(ventaMedicamento)
    }

    fun update(ventaMedicamento: VentaMedicamento){
        repository.insert(ventaMedicamento)
    }

    fun delete(ventaMedicamento: VentaMedicamento){
        repository.delete(ventaMedicamento)
    }

    fun getVentaMedicamentoByMedicamento(medicamentoId : Int) : LiveData<List<VentaMedicamento>>{
        return repository.getVentaMedicamentoByMedicamento(medicamentoId)
    }

    fun getVentaMedicamentoByEstablecimiento(establecimientoId : Int) : LiveData<List<VentaMedicamento>>{
        return repository.getVentaMedicamentoByEstablecimiento(establecimientoId)
    }
}