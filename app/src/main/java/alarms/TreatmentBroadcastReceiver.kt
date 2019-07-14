package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.lang.StringBuilder

class TreatmentBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "android.intent.action.BOOT_COMPLETED"){
            val notificationManager = NotificationsManager(context!!)
            //notificationManager.createAlarmForNotifications()
        }
    }
}