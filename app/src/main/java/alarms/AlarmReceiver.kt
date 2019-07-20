package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        val tratamiento = intent?.getStringExtra("Tratamiento")
        val medicamento = intent?.getStringExtra("Medicamento")
        val idToma = intent?.getIntExtra("IDToma", -1)

        Log.d("Alarma","Se disparo: " + tratamiento + " | " + medicamento + " | " + idToma)
        val notificationManager = NotificationsManager(context!!)
        notificationManager.sendNotification(tratamiento!!,medicamento!!, idToma!!)
    }
}