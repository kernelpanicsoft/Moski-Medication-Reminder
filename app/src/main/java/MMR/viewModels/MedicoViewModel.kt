package MMR.viewModels

import MMR.repositories.MedicoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.Medico

class MedicoViewModel (application: Application) : AndroidViewModel(application) {
    val repository : MedicoRepository = MedicoRepository(application)
    val allMedicos : LiveData<List<Medico>>

    init{
        allMedicos = repository.getAllMedicos()
    }

    fun insert(medico : Medico){
        repository.insert(medico)
    }

    fun update(medico : Medico){
        repository.update(medico)
    }

    fun delete(medico : Medico){
        repository.delete(medico)
    }

    fun deleteAllMedicos(){
        repository.deleteAllMedicos()
    }

    fun getMedico(id : Int) : LiveData<Medico>{
        return repository.getMedico(id)
    }
    fun getMedicosUsuario(id : Int) : LiveData<List<Medico>>{
        return repository.getMedicosUsuario(id)
    }
}