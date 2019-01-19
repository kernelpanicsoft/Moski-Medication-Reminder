package MMR.repositories

import MMR.daos.EstablecimientoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Update
import android.os.AsyncTask
import elements.Establecimiento
import model.MMRDataBase

class EstablecimientoRepository (application : Application){
    val establecimientoDao : EstablecimientoDao

    init{
        val database = MMRDataBase.getInstance(application)
        establecimientoDao = database.establecimientoDao()
    }

    fun insert(establecimiento : Establecimiento){
        InsertEstablecimientoAsyncTask(establecimientoDao).execute(establecimiento)
    }

    fun update(establecimiento: Establecimiento){
        UpdateEstablecimientoAsyncTask(establecimientoDao).execute(establecimiento)
    }

    fun delete(establecimiento: Establecimiento){
        DeleteEstablecimientoAsyncTask(establecimientoDao).execute(establecimiento)
    }

    fun deleteAllEstablecimientos(){
        DeleteAllEstablecimientosAsyncTask(establecimientoDao).execute()
    }

    fun getAllEstablecimientos() : LiveData<List<Establecimiento>>{
        return establecimientoDao.getAllEstablecimientos()
    }

    fun getEstablecimiento(id : Int) : LiveData<Establecimiento>{
        return establecimientoDao.getEstablecimiento(id)
    }

    private class InsertEstablecimientoAsyncTask constructor(private val establecimientoDao: EstablecimientoDao) : AsyncTask<Establecimiento, Void, Void>(){
        override fun doInBackground(vararg params: Establecimiento): Void? {
            establecimientoDao.insert(params[0])
            return null
        }
    }

    private class UpdateEstablecimientoAsyncTask constructor(private val establecimientoDao: EstablecimientoDao) : AsyncTask<Establecimiento, Void, Void>(){
        override fun doInBackground(vararg params: Establecimiento): Void? {
            establecimientoDao.update(params[0])
            return null
        }
    }

    private class DeleteEstablecimientoAsyncTask constructor(private val establecimientoDao: EstablecimientoDao) : AsyncTask<Establecimiento, Void, Void >(){
        override fun doInBackground(vararg params: Establecimiento): Void? {
            establecimientoDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllEstablecimientosAsyncTask constructor(private val establecimientoDao: EstablecimientoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void): Void? {
            establecimientoDao.deleteAllEstablecimientos()
            return null
        }
    }

}