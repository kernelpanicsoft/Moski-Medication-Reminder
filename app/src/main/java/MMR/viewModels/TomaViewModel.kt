package MMR.viewModels

import MMR.repositories.TomaRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.JoinTomasDelDia
import elements.Toma

class TomaViewModel (application: Application) : AndroidViewModel(application) {
    val repository : TomaRepository
    val allTomas : LiveData<List<Toma>>

    init{
        repository = TomaRepository(application)
        allTomas = repository.getAllTomas()
    }

    fun insert(toma : Toma){
        repository.insert(toma)
    }

    fun update(toma : Toma){
        repository.update(toma)
    }

    fun delete(toma : Toma){
        repository.delete(toma)
    }

    fun deleteAllTomas(){
        repository.deleteAllTomas()
    }

    fun deleteAllTomasTratamiento(tratamientoID : Int){
        repository.deleteAllTomasTratamiento(tratamientoID)
    }

    fun getToma( id : Int) : LiveData<Toma>{
        return repository.getToma(id)
    }


    fun getTomasTratamiento( idTramiento : Int) : LiveData<List<Toma>>{
        return repository.getTomasTratamiento(idTramiento)
    }

    fun getTomasDelDiaUsusuario( id: Int) : LiveData<List<JoinTomasDelDia>>{
        return repository.getTomasDelDiaUsuario(id)
    }

    fun updateTomaStatus(id:Int, status: Int){
        repository.updateTomaStatus(id,status)
    }

    fun resetAllTomasStatus(){
        repository.resetAllTomasStatus()
    }

    fun scheduleShotsNotifications() {
        repository.scheduleAlarmsForShots()
    }
}