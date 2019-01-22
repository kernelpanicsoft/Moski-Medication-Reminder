package MMR.viewModels

import MMR.repositories.TratamientoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.Tratamiento

class TratamientoViewModel (application: Application) : AndroidViewModel(application) {
    val repository : TratamientoRepository = TratamientoRepository(application)
    val allTratamientos : LiveData<List<Tratamiento>>

    init{
        allTratamientos = repository.getAllTratamientos(0)
    }

    fun insert(tratamiento: Tratamiento){
        repository.insert(tratamiento)
    }

    fun update(tratamiento: Tratamiento){
        repository.update(tratamiento)
    }

    fun delete(tratamiento: Tratamiento){
        repository.delete(tratamiento)
    }

    fun deleteAllTratamientos(){
        repository.deleteAllTratamientos()
    }

    fun deleteAllTratamientosUsuario(usuarioID : Int){
        repository.deleteAllTratamientosUsuario(usuarioID)
    }

    fun getTratamiento(tratamientoID : Int) : LiveData<Tratamiento>{
        return repository.getTratamiento(tratamientoID)
    }

    fun getAllTratamientosUsuario(usuarioID: Int) : LiveData<List<Tratamiento>>{
        return repository.getAllTratamientos(usuarioID)
    }
}