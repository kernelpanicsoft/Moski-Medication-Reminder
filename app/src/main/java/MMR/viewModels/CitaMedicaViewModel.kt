package MMR.viewModels

import MMR.repositories.CitaMedicaRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.CitaMedica

class CitaMedicaViewModel (application: Application) : AndroidViewModel(application) {
    val repository : CitaMedicaRepository = CitaMedicaRepository(application)
    val allCitasMedicas : LiveData<List<CitaMedica>>

    init{
        allCitasMedicas = repository.getAllCitasMedicas()
    }

    fun insert(citaMedica: CitaMedica){
        repository.insert(citaMedica)
    }

    fun update(citaMedica : CitaMedica){
        repository.update(citaMedica)
    }

    fun delete(citaMedica: CitaMedica){
        repository.delete(citaMedica)
    }

    fun deleteAllCitasMedicas(){
        repository.deleteAllCitasMedicas()
    }

    fun getMedico(id : Int) : LiveData<CitaMedica>{
        return repository.getCitaMedica(id)
    }

}