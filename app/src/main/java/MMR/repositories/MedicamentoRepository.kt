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
        InsertMedicamentoAsyncTask(medicamentoDao).execute(medicamento)
    }

    fun update(medicamento: Medicamento){
        UpdateMedicamentoAsyncTask(medicamentoDao).execute(medicamento)
    }

    fun delete(medicamento: Medicamento){
        DeleteMedicamentoAsyncTask(medicamentoDao).execute(medicamento)
    }

    fun deleteAllMedicamentos(){
        DeleteAllMedicamentosAsyncTask(medicamentoDao).execute()
    }

    fun getAllMedicamentos() : LiveData<List<Medicamento>>{
        return medicamentoDao.getAllMedicamentos()
    }

    fun getMedicamento( id : Int) : LiveData<Medicamento>{
        return medicamentoDao.getMedicamento(id)
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

    private class DeleteMedicamentoAsyncTask constructor(private val medicamentoDao: MedicamentoDao) : AsyncTask<Medicamento,Void, Void>(){
        override fun doInBackground(vararg params: Medicamento): Void? {
            medicamentoDao.delete(params[0])
            return null
        }
    }

    private class DeleteAllMedicamentosAsyncTask constructor(private val medicamentoDao: MedicamentoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            medicamentoDao.deleteAllMedicamentos()
            return null
        }
    }
}