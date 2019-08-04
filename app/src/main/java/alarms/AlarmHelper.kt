package alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import java.util.*

class AlarmHelper (val context: Context) {

    lateinit var alarmItent: PendingIntent
    var alarmMgr: AlarmManager? = null


    fun createAlarmForNotifications(hourOfDay: Int, minute: Int, tratamiento: String?, medicamento: String?, tomaID: Int?, recordatorio: Int?){
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmItent = Intent(context, AlarmReceiver::class.java).let{ intent ->
            intent.putExtra("Tratamiento", tratamiento)
            intent.putExtra("Medicamento", medicamento)
            intent.putExtra("IDToma", tomaID)
            intent.putExtra("Recordatorio", recordatorio)
            PendingIntent.getBroadcast(context,tomaID!!,intent, PendingIntent.FLAG_ONE_SHOT)

        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmItent
        )

    }

    fun createAlarmForAppointments(hourOfDay: Int, minute: Int, titulo: String?, doctor: String?,especialidad : String?, citaID: Int?, recordatorio: Int?){
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmItent = Intent(context, AppointmentAlarmReceiver::class.java).let{ intent ->
            intent.putExtra("Titulo", titulo)
            intent.putExtra("Doctor", doctor)
            intent.putExtra("Especialidad", especialidad)
            intent.putExtra("Recordatorio", recordatorio)
            intent.putExtra("IDCita", citaID)
            PendingIntent.getBroadcast(context,citaID!!,intent, PendingIntent.FLAG_ONE_SHOT)

        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmItent
        )

    }


    fun cancelAlarm(){
        alarmMgr?.cancel(alarmItent)
    }

    fun enableReceiver(){
        val receiver = ComponentName(context, TreatmentBroadcastReceiver::class.java)

        context.packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP

        )
    }

    fun disableReceiver(){
        val receiver = ComponentName(context, TreatmentBroadcastReceiver::class.java)

        context.packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP

        )
    }

}