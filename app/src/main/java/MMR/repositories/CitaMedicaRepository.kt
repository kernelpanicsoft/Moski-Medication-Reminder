package MMR.repositories

import MMR.daos.CitaMedicaDao
import alarms.AlarmHelper
import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import elements.CitaMedica
import model.MMRDataBase
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class CitaMedicaRepository (application: Application) {
    val citaMedicoDao : CitaMedicaDao
    val alarmHelper : AlarmHelper

    init{
        val database = MMRDataBase.getInstance(application)
        citaMedicoDao = database.citaMedicaDao()
        alarmHelper = AlarmHelper(application)
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

    fun getCitasCount(id : Int ) : LiveData<Int>{
        return citaMedicoDao.getCitasCount(id)
    }

    fun scheduleAlarmsForAppointments(){
        scheduleAlarmsForAppointmentsAsyncTask(citaMedicoDao).execute()
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

    private inner class scheduleAlarmsForAppointmentsAsyncTask constructor(private val citaMedicaDao: CitaMedicaDao) : AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {

            val sdf = SimpleDateFormat("HH:mm:ss")
            var appointmentDate : Date
            val cal = Calendar.getInstance()

            val currentLocalTime = LocalTime.now()

            for(appointment in citaMedicaDao.getCitasProgramadasWithoutLiveData()){
                appointmentDate = sdf.parse(appointment.hora)
                cal.time = appointmentDate
                Log.d("AlarmaCita",appointment.fecha + " " +  appointment.hora + " " + appointment.tipoRecordatorio)


                val appointmentTime = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
                if(appointmentTime.isAfter(currentLocalTime)){
                    Log.d("AlarmaCita", "Estas invocando registro alarma despues de la hora actual " + currentLocalTime.toString())
                    alarmHelper.createAlarmForAppointments(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), appointment.titulo, appointment.doctor, appointment.especialidad, appointment.id, appointment.tipoRecordatorio)

                }
            }

            return null
        }
    }

}