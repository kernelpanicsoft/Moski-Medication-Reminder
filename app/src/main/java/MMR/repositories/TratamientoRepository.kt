package MMR.repositories

import MMR.daos.TratamientoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.Tratamiento
import model.MMRDataBase

class TratamientoRepository (application: Application) {
    val tratamientoDao : TratamientoDao

    init{
        val database = MMRDataBase.getInstance(application)
        tratamientoDao = database.tratamientoDao()
    }

    fun insert(tratamiento: Tratamiento){
        InsertTratamientoAsyncTask(tratamientoDao).execute(tratamiento)
    }

    fun update(tratamiento: Tratamiento){
        UpdateTratamientoAsyncTask(tratamientoDao).execute(tratamiento)
    }

    fun delete(tratamiento: Tratamiento){
        DeleteTratamientoAsyncTask(tratamientoDao).execute(tratamiento)
    }

    fun deleteAllTratamientos(){
        DeleteAllTratamientosAsyncTask(tratamientoDao).execute()
    }

    fun deleteAllTratamientosUsuario(usuarioID: Int){
        DeleteTratamientosDeUsuarioAsyncTask(tratamientoDao).execute(usuarioID)
    }

    fun getAllTratamientos(usuarioID: Int) : LiveData<List<Tratamiento>>{
        return tratamientoDao.getTratamientosUsuario(usuarioID)
    }

    fun getTratamiento(id : Int) : LiveData<Tratamiento>{
        return tratamientoDao.getTratamiento(id)
    }

    private class InsertTratamientoAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Tratamiento, Void, Void>(){
        override fun doInBackground(vararg params: Tratamiento): Void?{
            tratamientoDao.insert(params[0])
            return null
        }
    }

    private class UpdateTratamientoAsyncTask constructor(private val tratamientoDao : TratamientoDao) : AsyncTask<Tratamiento, Void, Void>(){
        override fun doInBackground(vararg params: Tratamiento): Void? {
            tratamientoDao.update(params[0])
            return null
        }
    }

    private class DeleteTratamientoAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Tratamiento, Void, Void>(){
        override fun doInBackground(vararg params: Tratamiento): Void? {
            tratamientoDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllTratamientosAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            tratamientoDao.deleteAllTratamientos()
            return null
        }
    }

    private class DeleteTratamientosDeUsuarioAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?) : Void? {
            tratamientoDao.deleteAllTratamientosUsuario(params[0])
            return null
        }
    }
}