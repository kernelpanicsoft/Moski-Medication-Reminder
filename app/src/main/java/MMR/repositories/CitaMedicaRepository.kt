package MMR.repositories

import MMR.daos.CitaMedicaDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.CitaMedica
import model.MMRDataBase

class CitaMedicaRepository (application: Application) {
    val citaMedicoDao : CitaMedicaDao

    init{
        val database = MMRDataBase.getInstance(application)
        citaMedicoDao = database.citaMedicaDao()
    }

    fun insert(citaMedica : CitaMedica){
        InsertCitaMedicaAsyncTask(citaMedicoDao).execute(citaMedica)
    }

    fun update(citaMedica: CitaMedica){
        UpdateCitaMedicaAsyncTask(citaMedicoDao).execute(citaMedica)
    }

    fun delete(citaMedica: CitaMedica){
        DeleteCitaMedicaAsyncTask(citaMedicoDao).execute(citaMedica)
    }

    fun deleteAllCitasMedicas(){
        DeleteAllCitasMedicasAsyncTask(citaMedicoDao).execute()
    }

    fun getAllCitasMedicas() : LiveData<List<CitaMedica>>{
        return citaMedicoDao.getAllCitasMedicas()
    }

    fun getCitaMedica(id : Int) : LiveData<CitaMedica>{
        return citaMedicoDao.getCitaMedica(id)
    }

    fun getCitasUsuario(id : Int) : LiveData<List<CitaMedica>>{
        return citaMedicoDao.getCitasUsuario(id)
    }

    private class InsertCitaMedicaAsyncTask constructor(private val citaMedicaDao: CitaMedicaDao) : AsyncTask<CitaMedica, Void, Void>(){
        override fun doInBackground(vararg params: CitaMedica): Void? {
            citaMedicaDao.insert(params[0])
            return null
        }
    }

    private class UpdateCitaMedicaAsyncTask constructor(private val citaMedicaDao: CitaMedicaDao) : AsyncTask<CitaMedica, Void, Void>(){
        override fun doInBackground(vararg params: CitaMedica): Void? {
            citaMedicaDao.update(params[0])
            return null
        }
    }

    private class DeleteCitaMedicaAsyncTask constructor(private val citaMedicaDao: CitaMedicaDao) : AsyncTask<CitaMedica, Void, Void>(){
        override fun doInBackground(vararg params: CitaMedica): Void? {
            citaMedicaDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllCitasMedicasAsyncTask constructor(private val citaMedicaDao: CitaMedicaDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void): Void? {
            citaMedicaDao.deleteAllCitasMedicas()
            return null
        }
    }

}