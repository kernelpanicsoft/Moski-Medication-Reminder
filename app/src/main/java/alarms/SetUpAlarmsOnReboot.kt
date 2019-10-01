package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SetUpAlarmsOnReboot : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val setupShoots = Intent(context, TomasSchedulerService::class.java)
        context?.startService(setupShoots)

        val setUpAppointments = Intent(context, CitasSchedulerService::class.java)
        context?.startService(setUpAppointments)

    }
}