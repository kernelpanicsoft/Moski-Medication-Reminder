package MMR.repositories

import MMR.daos.UsuarioDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.Usuario
import model.MMRDataBase

class UsuarioRepository(application: Application) {
    val usuarioDao : UsuarioDao

    init{
        val database = MMRDataBase.getInstance(application)
        usuarioDao = database.usuarioDao()
    }


    fun insert(usuario : Usuario){
        InsertUsuarioAsynTask(usuarioDao).execute(usuario)
    }

    fun update(usuario : Usuario){
        UpdateUsuarioAsyncTask(usuarioDao).execute(usuario)
    }

    fun delete(usuario : Usuario){
        DeleteUsuarioAsyncTask(usuarioDao).execute(usuario)
    }

    fun deleteAllUsuarios(){
        DeleteAllUsuariosAsyncTask(usuarioDao).execute()
    }

    fun getAllUsuarios() : LiveData<List<Usuario>>{
        return usuarioDao.getAllUsuarios()
    }

    fun getUsuario( id : Int) : LiveData<Usuario>{
        return usuarioDao.getUsuario(id)
    }

    private class InsertUsuarioAsynTask constructor(private val usuarioDao: UsuarioDao) : AsyncTask<Usuario, Void, Void>(){
        override fun doInBackground(vararg usuarios: Usuario): Void? {
            usuarioDao.insert(usuarios[0])
            return null
        }
    }

    private class UpdateUsuarioAsyncTask constructor(private val usuarioDao: UsuarioDao) : AsyncTask<Usuario, Void, Void>(){
        override fun doInBackground(vararg params: Usuario): Void? {
            usuarioDao.update(params[0])
            return null
        }
    }

    private class DeleteUsuarioAsyncTask constructor(private val usuarioDao : UsuarioDao) : AsyncTask<Usuario, Void, Void>(){
        override fun doInBackground(vararg params: Usuario): Void? {
            usuarioDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllUsuariosAsyncTask constructor(private val usuarioDao: UsuarioDao ) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            usuarioDao.deleteAllUsuarios()
            return null
        }
    }

}