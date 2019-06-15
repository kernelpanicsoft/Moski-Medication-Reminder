package MMR.viewModels

import MMR.repositories.TratamientoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.JoinMedicamentoTratamientoData
import elements.Tratamiento

class TratamientoViewModel (application: Application) : AndroidViewModel(application) {
    val repository : TratamientoRepository = TratamientoRepository(application)


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

    fun getTratamientosUsuario(usuarioID: Int) : LiveData<List<JoinMedicamentoTratamientoData>>{
        return repository.getTratamientosUsuario(usuarioID)
    }
    fun getLastID() : LiveData<Long>{
        return repository.getLastInsertedID()
    }

    fun incrementTomasATiempo(id: Int){
        repository.incrementTomasATiempo(id)
    }

    fun incrementTomasPospuestas(id: Int){
        repository.incrementTomasPospuestas(id)
    }

    fun incrementTomasOmitidas(id: Int){
        repository.incrementTomasOmitidas(id)
    }
}