package MMR.repositories

import MMR.daos.VentaMedicamentoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.VentaMedicamento
import model.MMRDataBase

class VentaMedicamentoRepository (application: Application) {
    val ventaMedicamentoDao : VentaMedicamentoDao

    init{
        val database = MMRDataBase.getInstance(application)
        ventaMedicamentoDao = database.ventaMedicamentoDao()
    }

    fun insert(ventaMedicamento: VentaMedicamento){
        InsertVentaMedicamentoAsyncTask(ventaMedicamentoDao).execute(ventaMedicamento)
    }

    fun update(ventaMedicamento: VentaMedicamento){
        UpdateVentaMedicamentoAsyncTask(ventaMedicamentoDao).execute(ventaMedicamento)
    }

    fun delete(ventaMedicamento: VentaMedicamento){
        DeleteVentaMedicamentoAsyncTask(ventaMedicamentoDao).execute(ventaMedicamento)
    }

    fun getAllVentasMedicamento() : LiveData<List<VentaMedicamento>>{
        return ventaMedicamentoDao.getAllVentasMedicamento()
    }

    fun getVentaMedicamentoByMedicamento(medicamentoId : Int) : LiveData<List<VentaMedicamento>>{
        return ventaMedicamentoDao.getVentaMedicamentoByMedicamento(medicamentoId)
    }

    fun getVentaMedicamentoByEstablecimiento(establecimientoId : Int) : LiveData<List<VentaMedicamento>>{
        return ventaMedicamentoDao.getVentaMedicamentoByEstablecimiento(establecimientoId)
    }

    private class InsertVentaMedicamentoAsyncTask constructor(private val ventaMedicamentoDao: VentaMedicamentoDao) : AsyncTask<VentaMedicamento, Void, Void>(){
        override fun doInBackground(vararg params: VentaMedicamento): Void? {
            ventaMedicamentoDao.insert(params[0])
            return null
        }
    }

    private class UpdateVentaMedicamentoAsyncTask constructor(private val ventaMedicamentoDao: VentaMedicamentoDao) : AsyncTask<VentaMedicamento, Void, Void>(){
        override fun doInBackground(vararg params: VentaMedicamento): Void? {
            ventaMedicamentoDao.update(params[0])
            return null
        }
    }

    private class DeleteVentaMedicamentoAsyncTask constructor(private val ventaMedicamentoDao: VentaMedicamentoDao) : AsyncTask<VentaMedicamento, Void, Void>(){
        override fun doInBackground(vararg params: VentaMedicamento): Void? {
            ventaMedicamentoDao.delete(params[0])
            return null
        }
    }


}