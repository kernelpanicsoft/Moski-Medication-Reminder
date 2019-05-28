package MMR.repositories

import MMR.daos.MedicoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Update
import android.os.AsyncTask
import elements.Medico
import model.MMRDataBase

class MedicoRepository (application : Application) {
    val medicoDao : MedicoDao

    init{
        val database = MMRDataBase.getInstance(application)
        medicoDao = database.medicoDao()
    }

    fun insert(medico : Medico){
        InsertMedicoAsyncTask(medicoDao).execute(medico)
    }

    fun update(medico : Medico){
        UpdateMedicoAsyncTask(medicoDao).execute(medico)
    }

    fun delete(medico : Medico){
        DeleteMedicoAsyncTask(medicoDao).execute(medico)
    }

    fun deleteAllMedicos(){
        DeleteAllMedicamentosAsyncTask(medicoDao).execute()
    }

    fun getAllMedicos() : LiveData<List<Medico>>{
        return medicoDao.getAllMedicos()
    }

    fun getMedico(id : Int) : LiveData<Medico>{
        return medicoDao.getMedico(id)
    }

    fun getMedicosUsuario(id : Int) : LiveData<List<Medico>>{
        return medicoDao.getUserMedicos(id)
    }

    fun getLastInsertedID(): LiveData<Long>{
        return medicoDao.getLastID()
    }

    private class InsertMedicoAsyncTask constructor(private val medicoDao: MedicoDao) : AsyncTask<Medico, Void, Void>(){
        override fun doInBackground(vararg params: Medico): Void? {
            medicoDao.insert(params[0])
            return null
        }
    }

    private class UpdateMedicoAsyncTask constructor(private val medicoDao: MedicoDao) : AsyncTask<Medico, Void, Void>(){
        override fun doInBackground(vararg params: Medico): Void? {
            medicoDao.update(params[0])
            return null
        }
    }

    private class DeleteMedicoAsyncTask constructor(private val medicoDao: MedicoDao) : AsyncTask<Medico, Void, Void>(){
        override fun doInBackground(vararg params: Medico): Void? {
            medicoDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllMedicamentosAsyncTask constructor(private val medicoDao: MedicoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            medicoDao.deleteAllMedicos()
            return null
        }
    }
}