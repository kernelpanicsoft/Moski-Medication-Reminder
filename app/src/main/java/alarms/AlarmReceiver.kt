package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("Alarma","Se disparo")
        val notificationManager = NotificationsManager(context!!)
        notificationManager.sendNotification("Hola",intent!!.getStringExtra("Tratamiento"))
    }
}