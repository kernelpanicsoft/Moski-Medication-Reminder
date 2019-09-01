package MMR.viewModels

import MMR.repositories.MedicamentoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.Medicamento

class MedicamentoViewModel (application: Application) : AndroidViewModel(application) {

    val repository : MedicamentoRepository
    val allMedicamentos : LiveData<List<Medicamento>>

    init {
        repository =  MedicamentoRepository(application)
        allMedicamentos = repository.getAllMedicamentos()
    }

    fun insert(medicamento: Medicamento){
        repository.insert(medicamento)
    }

    fun update(medicamento: Medicamento){
        repository.update(medicamento)
    }

    fun delete(medicamento: Medicamento){
        repository.delete(medicamento)
    }

    fun deleteAllMedicamentos(){
        repository.deleteAllMedicamentos()
    }

    fun getMedicamento(id : Int) : LiveData<Medicamento>{
        return repository.getMedicamento(id)
    }

    fun getMedicamentosUsuario(id : Int) : LiveData<List<Medicamento>>{
        return repository.getMedicamentosUsuario(id)
    }

    fun getCuentaMedicamentos(id: Int) : LiveData<Int>{
        return repository.getMedicamentosCount(id)
    }
}
