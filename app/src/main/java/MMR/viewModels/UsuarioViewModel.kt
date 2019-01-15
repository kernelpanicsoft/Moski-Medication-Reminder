package MMR.viewModels

import MMR.repositories.UsuarioRepository
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import elements.Usuario

class UsuarioViewModel (application: Application) : AndroidViewModel(application){
    val repository : UsuarioRepository
    val allUsuarios : LiveData<List<Usuario>>

    init{
        repository = UsuarioRepository(application)
        allUsuarios = repository.getAllUsuarios()
    }

    fun insert(usuario: Usuario){
        repository.insert(usuario)
    }

    fun update(usuario: Usuario){
        repository.update(usuario)
    }

    fun delete(usuario: Usuario){
        repository.delete(usuario)
    }

    fun deleteAllUsuarios(){
        repository.deleteAllUsuarios()
    }

    fun getUsuario(id : Int) : LiveData<Usuario>{
       return repository.getUsuario(id)
    }

}