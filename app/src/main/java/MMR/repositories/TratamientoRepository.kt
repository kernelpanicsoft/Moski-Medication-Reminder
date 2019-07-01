package MMR.repositories

import MMR.daos.TratamientoDao
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import elements.JoinMedicamentoTratamientoData
import elements.Tratamiento
import model.MMRDataBase


class TratamientoRepository (application: Application) {

    val tratamientoDao : TratamientoDao

    init{
        val database = MMRDataBase.getInstance(application)
        tratamientoDao = database.tratamientoDao()
    }

    fun insert(tratamiento: Tratamiento) {
        InsertTratamientoAsyncTask(tratamientoDao).execute(tratamiento)
    }

    fun update(tratamiento: Tratamiento){
        UpdateTratamientoAsyncTask(tratamientoDao).execute(tratamiento)
    }

    fun incrementTomasATiempo(id: Int){
        IncrementTomasATiempoAsyncTask(tratamientoDao).execute(id)
    }

    fun incrementTomasPospuestas(id: Int){
        IncrementTomasPospuestasAsyncTask(tratamientoDao).execute(id)
    }

    fun incrementTomasOmitidas(id: Int){
        IncrementTomasOmitidasAsyncTask(tratamientoDao).execute(id)
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

    fun endsAllTratamientos(){
        EndsTratamientosAsyncTask(tratamientoDao).execute()
    }

    fun decreaseTreatmentsOneDay(){
        DecreaseDiasTratamientoAsyncTask(tratamientoDao).execute()
    }

    fun getAllTratamientos(usuarioID: Int) : LiveData<List<Tratamiento>>{
        return tratamientoDao.getTratamientosUsuario(usuarioID)
    }

    fun getTratamiento(id : Int) : LiveData<Tratamiento>{
        return tratamientoDao.getTratamiento(id)
    }

    fun getLastInsertedID() : LiveData<Long>{
        return tratamientoDao.getLastID()
    }

    fun getTratamientosUsuario(id: Int) : LiveData<List<JoinMedicamentoTratamientoData>>{
        return tratamientoDao.getTratamientosConMedicamentoUsuario(id)
    }
    private class InsertTratamientoAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Tratamiento, Void, Long>(){
        override fun doInBackground(vararg params: Tratamiento): Long?{
            return tratamientoDao.insert(params[0])
        }
    }

    private class UpdateTratamientoAsyncTask constructor(private val tratamientoDao : TratamientoDao) : AsyncTask<Tratamiento, Void, Void>(){
        override fun doInBackground(vararg params: Tratamiento): Void? {
            tratamientoDao.update(params[0])
            return null
        }
    }

    private class IncrementTomasATiempoAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            tratamientoDao.incrementTomasATiempo(params[0]!!)
            return null
        }
    }

    private class IncrementTomasPospuestasAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            tratamientoDao.incrementTomasPospuestas(params[0]!!)
            return null
        }
    }

    private class IncrementTomasOmitidasAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            tratamientoDao.incrementTomasOmitidas(params[0]!!)
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

    private class EndsTratamientosAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            tratamientoDao.endsTreatments()
            return null
        }
    }

    private class DecreaseDiasTratamientoAsyncTask constructor(private val tratamientoDao: TratamientoDao) : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            tratamientoDao.decreaseTreatmentDays()
            return null
        }
    }


}