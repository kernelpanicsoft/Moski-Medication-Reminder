package MMR.repositories

import MMR.daos.TomaDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.JoinTomasDelDia
import elements.Toma
import model.MMRDataBase

class TomaRepository(application: Application) {
    val tomaDao : TomaDao

    init{
        val database = MMRDataBase.getInstance(application)
        tomaDao = database.tomaDao()
    }

    fun insert(toma : Toma){
        InsertTomaAsyncTask(tomaDao).execute(toma)
    }

    fun update(toma : Toma){
        UpdateTomaAsyncTask(tomaDao).execute(toma)
    }

    fun delete(toma : Toma){
        DeleteTomaAsyncTask(tomaDao).execute(toma)
    }

    fun deleteAllTomas(){
        DeleteAllTomasAsyncTask(tomaDao).execute()
    }

    fun deleteAllTomasTratamiento(tratamientoID : Int){
        DeleteAllTomasTratamientoAsyncTask(tomaDao).execute(tratamientoID)
    }

    fun getAllTomas() : LiveData<List<Toma>> {
        return tomaDao.getAllTomas()
    }

    fun getToma( id : Int) :  LiveData<Toma>{
        return tomaDao.getToma(id)
    }

    fun getTomasTratamiento( idTratamiento : Int) : LiveData<List<Toma>>{
        return tomaDao.getTomasTratamiento(idTratamiento)
    }

    fun getTomasDelDiaUsuario(id: Int) : LiveData<List<JoinTomasDelDia>>{
        return tomaDao.getTomasDia(id)
    }

    fun updateTomaStatus(idToma: Int, status: Int){
        UpdateStatusTomaAsyncTask(tomaDao,idToma,status).execute()
    }

    fun  resetAllTomasStatus(){
        ResetStatusTomasAsyncTask(tomaDao).execute()
    }

    private class InsertTomaAsyncTask constructor(private val tomaDao: TomaDao) : AsyncTask<Toma, Void, Void>(){
        override fun doInBackground(vararg params: Toma): Void? {
            tomaDao.insert(params[0])
            return null
        }
    }

    private class UpdateTomaAsyncTask constructor(private val tomaDao : TomaDao) : AsyncTask<Toma, Void, Void>(){
        override fun doInBackground(vararg params: Toma): Void? {
            tomaDao.update(params[0])
            return null
        }
    }

    private class UpdateStatusTomaAsyncTask constructor(private val tomaDao: TomaDao, private val idToma: Int,private val statusToma: Int) : AsyncTask<Int,Void,Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            tomaDao.updateTomaStatus(idToma,statusToma)
            return null
        }
    }

    private class ResetStatusTomasAsyncTask constructor(private val tomaDao: TomaDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            tomaDao.resetTomasStatus()
            return null
        }
    }

    private class DeleteTomaAsyncTask constructor(private val tomaDao: TomaDao) : AsyncTask<Toma, Void, Void>(){
        override fun doInBackground(vararg params: Toma): Void? {
            tomaDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllTomasAsyncTask constructor(private val tomaDao: TomaDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            tomaDao.deleteAllTomas()
            return null
        }
    }


    private class DeleteAllTomasTratamientoAsyncTask constructor(private val tomaDao: TomaDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            tomaDao.deleteAllTomasTratamiento(params[0])
            return null
        }
    }
}