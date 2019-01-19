package MMR.viewModels

import MMR.repositories.EstablecimientoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.Establecimiento

class EstablecimientoViewModel (application : Application) : AndroidViewModel(application){
    val repository : EstablecimientoRepository = EstablecimientoRepository(application)
    val allEstablecimientos : LiveData<List<Establecimiento>>

    init {
        allEstablecimientos = repository.getAllEstablecimientos()
    }

    fun insert(establecimiento: Establecimiento){
        repository.insert(establecimiento)
    }

    fun update(establecimiento: Establecimiento){
        repository.update(establecimiento)
    }

    fun delete(establecimiento: Establecimiento){
        repository.delete(establecimiento)
    }

    fun deleteAllEstablecimientos(){
        repository.deleteAllEstablecimientos()
    }

    fun getEstablecimiento(id : Int) : LiveData<Establecimiento>{
        return repository.getEstablecimiento(id)
    }
}