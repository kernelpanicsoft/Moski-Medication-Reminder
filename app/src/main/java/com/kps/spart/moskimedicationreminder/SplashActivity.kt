package com.kps.spart.moskimedicationreminder

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
          //  Log.d("EstasReiniciando","Estas iniciando la actualizacion de tomas " + firstRun.toString())
        }

        val channelManager = NotificationsManager(this)
        channelManager.createNotificationChannel()

        startActivity(Intent (this@SplashActivity,MainActivity::class.java))
        finish()
    }


    fun checkDateForRunService(){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        val fechaInicioUso = sharedPref.getString("firstrundate", "")
        //Toast.makeText(this, fechaInicioUso, Toast.LENGTH_SHORT).show()

        val sdf = SimpleDateFormat.getDateInstance()
        val todayDate = Calendar.getInstance().time
        val savedDate = sdf.parse(fechaInicioUso)

        val auxDate = sdf.parse(sdf.format(todayDate))
        Log.d("ComparaFechas",auxDate.compareTo(savedDate).toString() + " " + auxDate.time.toString() + " : " + savedDate.time.toString())
       // Log.d("ComparaFechas", auxDate.toString().equals(savedDate.toString()).toString())

        if(auxDate.compareTo(savedDate) != 0){
            with(sharedPref.edit()){
                putString("firstrundate", sdf.format(Calendar.getInstance().time))
                apply()
            }
            val restartShots = Intent(this,MMRReiniciarDiaService::class.java)
            this.startService(restartShots)

            val tomaViewModel = ViewModelProviders.of(this).get(TomaViewModel::class.java)
            tomaViewModel.scheduleShotsNotifications()

            Log.d("ComparaFechas","Estas reiniciando tomas" + todayDate.compareTo(savedDate) + " @ " + todayDate.time.toString() + " : " + savedDate.time.toString())
        }else if(todayDate.compareTo(savedDate) == 0){
           // Log.d("EstasReiniciando","Las fechas son iguales")

        }

    }



}