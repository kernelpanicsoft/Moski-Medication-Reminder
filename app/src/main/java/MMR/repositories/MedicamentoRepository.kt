package MMR.repositories

import MMR.daos.MedicamentoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.Medicamento
import model.MMRDataBase

class MedicamentoRepository (application: Application){
    val medicamentoDao : MedicamentoDao

    init{
        val database = MMRDataBase.getInstance(application)
        medicamentoDao = database.medicamentoDao()
    }

    fun insert(medicamento : Medicamento){

    }

    fun update(medicamento: Medicamento){

    }

    fun delete(medicamento: Medicamento){

    }

    fun deleteAllMedicamentos(){

    }

    fun getAllMedicamentos() : LiveData<List<Medicamento>>{
        return medicamentoDao.getAllMedicamentos()
    }

    private class InsertMedicamentoAsyncTask constructor(private val medicamentoDao: MedicamentoDao) : AsyncTask<Medicamento, Void, Void>(){
        override fun doInBackground(vararg params: Medicamento): Void? {
            medicamentoDao.insert(params[0])
            return null
        }
    }

    private class UpdateMedicamentoAsyncTask constructor(private val medicamentoDao: MedicamentoDao) : AsyncTask<Medicamento, Void, Void>(){
        override fun doInBackground(vararg params: Medicamento): Void? {
            medicamentoDao.update(params[0])
            return null
        }
    }

    private class DeleteAllUsuariosAsyncTask constructor(private val medicamentoDao: MedicamentoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            medicamentoDao.deleteAllMedicamentos()
            return null
        }
    }
}