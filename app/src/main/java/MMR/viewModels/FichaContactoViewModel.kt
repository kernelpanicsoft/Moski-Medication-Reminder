package MMR.viewModels

import MMR.repositories.FichaContactoRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.FichaContacto

class FichaContactoViewModel (application: Application, MedicoID : Int) : AndroidViewModel(application) {
    val repository : FichaContactoRepository = FichaContactoRepository(application)
    val allFichas : LiveData<List<FichaContacto>>

    init{
        allFichas = repository.getAllFichasContacto(MedicoID)
    }

    fun insert(fichaContacto: FichaContacto){
        repository.insert(fichaContacto)
    }

    fun update(fichaContacto: FichaContacto){
        repository.update(fichaContacto)
    }

    fun delete(fichaContacto: FichaContacto){
        repository.delete(fichaContacto)
    }

    fun deleteAllFichasContacto(MedicId : Int){
        repository.deleteAllFichasContacto(MedicId)
    }

    fun getFichaContacto(id : Int) : LiveData<FichaContacto>{
        return repository.getFichaContacto(id)
    }

    fun getFichasContacto(MedicId : Int) : LiveData<List<FichaContacto>>{
        return repository.getAllFichasContacto(MedicId)
    }

}