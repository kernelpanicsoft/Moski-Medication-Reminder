package MMR.repositories

import MMR.daos.FichaContactoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.FichaContacto
import model.MMRDataBase


class FichaContactoRepository (application: Application){
    val fichaContactoDao : FichaContactoDao

    init{
        val database = MMRDataBase.getInstance(application)
        fichaContactoDao = database.fichaContactoDao()
    }

    fun insert(fichaContacto : FichaContacto){
        InsertFichaContactoAsyncTask(fichaContactoDao).execute(fichaContacto)
    }

    fun update(fichaContacto: FichaContacto){
        UpdateFichaContactoAsyncTask(fichaContactoDao).execute(fichaContacto)
    }

    fun delete(fichaContacto: FichaContacto){
        DeleteFichaContactoAsyncTask(fichaContactoDao).execute(fichaContacto)
    }

    fun deleteAllFichasContacto(MedicID : Int){
        DeleteFichasDeContactoAsyncTask(fichaContactoDao).execute(MedicID)
    }

    fun getAllFichasContacto(medicId : Int) : LiveData<List<FichaContacto>>{
        return fichaContactoDao.getFichasContactoDeMedico(medicId)
    }

    fun getFichaContacto(id : Int) : LiveData<FichaContacto>{
        return fichaContactoDao.getFichaContacto(id)
    }

    private class InsertFichaContactoAsyncTask constructor(private val fichaContactoDao: FichaContactoDao) : AsyncTask<FichaContacto, Void, Void>(){
        override fun doInBackground(vararg params: FichaContacto): Void? {
            fichaContactoDao.insert(params[0])
            return null
        }
    }

    private class UpdateFichaContactoAsyncTask constructor(private val fichaContactoDao : FichaContactoDao) : AsyncTask<FichaContacto, Void, Void>(){
        override fun doInBackground(vararg params: FichaContacto): Void? {
            fichaContactoDao.update(params[0])
            return null
        }
    }

    private class DeleteFichaContactoAsyncTask constructor(private val fichaContactoDao : FichaContactoDao) : AsyncTask<FichaContacto, Void, Void>(){
        override fun doInBackground(vararg params: FichaContacto): Void? {
            fichaContactoDao.delete(params[0])
            return null
        }
    }

    private class DeleteFichasDeContactoAsyncTask constructor(private val fichaContactoDao : FichaContactoDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            fichaContactoDao.deleteAllFichasContacto(params[0])
            return null
        }
    }
}