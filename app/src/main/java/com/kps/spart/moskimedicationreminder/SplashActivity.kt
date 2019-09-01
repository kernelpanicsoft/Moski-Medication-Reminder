package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
import MMR.viewModels.TomaViewModel
import alarms.NotificationsManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
       // val firstRun = !sharedPref.getBoolean("firstrun", true)
        if(!sharedPref.getBoolean("firstrun", true)){
            checkDateForRunService()
            Log.d("EstasReiniciando","Estas iniciando la actualizacion de tomas ")
        }else{
            Log.d("EstasReiniciando", "no se está reiniciando")
        }

        val channelManager = NotificationsManager(this)
        channelManager.createNotificationChannel()

        startActivity(Intent (this@SplashActivity,MainActivity::class.java))
        finish()
    }


    fun checkDateForRunService(){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        val fechaInicioUso = sharedPref.getString("firstrundate", "")

        val sdf = SimpleDateFormat.getDateInstance()
        val todayDate = Calendar.getInstance().time
        val savedDate = sdf.parse(fechaInicioUso)

        val auxDate = sdf.parse(sdf.format(todayDate))
        Log.d("ComparaFechas",auxDate.compareTo(savedDate).toString() + " # " + auxDate.time.toString() + " : " + savedDate.time.toString())

        if(auxDate.compareTo(savedDate) != 0){
            Log.d("ComparaFechas","Las fechas no son iguales")
            with(sharedPref.edit()){
                putString("firstrundate", sdf.format(Calendar.getInstance().time))
                apply()
            }

            Log.d("ComparaFechas","Estas invocando el servicio")
            val restartShots = Intent(this,MMRReiniciarDiaService::class.java)
            this.startService(restartShots)

            val tomaViewModel = ViewModelProviders.of(this).get(TomaViewModel::class.java)
            tomaViewModel.scheduleShotsNotifications()

            val citasViewModel = ViewModelProviders.of(this).get(CitaMedicaViewModel::class.java)
            citasViewModel.scheduleCitasAlarms()

            Log.d("ComparaFechas","Estas reiniciando tomas" + auxDate.compareTo(savedDate) + " @ " + auxDate.time.toString() + " : " + savedDate.time.toString())
        }else if(auxDate.compareTo(savedDate) == 0){
            Log.d("ComparaFechas","Las fechas son iguales")

        }else{
            Log.d("ComparaFechas","No está entrando " + auxDate.compareTo(savedDate))
        }

    }



}