package alarms

import android.app.*
import android.content.*
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.AsyncTask
import android.support.v4.app.NotificationCompat
import com.kps.spart.moskimedicationreminder.MainActivity
import com.kps.spart.moskimedicationreminder.R
import model.ACTION_UPDATE_NOTIFICATION
import model.CANAL_PRIMARIO_ID
import model.NOTIFICACION_ID
import java.util.*

class NotificationsManager(val context: Context) {
    lateinit var mNotifyManager : NotificationManager
    lateinit var alarmItent: PendingIntent
    var alarmMgr: AlarmManager? = null



    fun createNotificationChannel(){
        mNotifyManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CANAL_PRIMARIO_ID,"Moski medication reminder", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification from MMR to shots and medical appointments"
            mNotifyManager.createNotificationChannel(notificationChannel)

        }
    }

    fun getNotificationBuilder(title: String, content: String) : NotificationCompat.Builder{
        val notificationIntent = Intent(context, MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(context, NOTIFICACION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notifyBuilder = NotificationCompat.Builder(context, CANAL_PRIMARIO_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_notification_capsule)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        return notifyBuilder
    }

    fun sendNotification(title: String, content: String){
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(context, NOTIFICACION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)
        val notifyBuilder = getNotificationBuilder(title,content)
        notifyBuilder.addAction(R.drawable.ic_capsula, context.getString(R.string.tomar), updatePendingIntent)
        notifyBuilder.addAction(R.drawable.ic_capsula,context.getString(R.string.saltar), updatePendingIntent)
        notifyBuilder.addAction(R.drawable.ic_capsula,context.getString(R.string.posponer), updatePendingIntent)
        mNotifyManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyManager.notify(NOTIFICACION_ID, notifyBuilder.build())
    }


    fun cancelNotification(){
        mNotifyManager.cancel(NOTIFICACION_ID)
    }

    fun requestShotsForDailyNotifications(){
        alarmItent = Intent(context, AlarmReceiver::class.java).let{ intent ->
            PendingIntent.getBroadcast(context,0,intent, 0)
        }

    }

    fun createAlarmForNotifications(hourOfDay: Int, minute: Int){
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmItent = Intent(context, AlarmReceiver::class.java).let{ intent ->
            intent.putExtra("Tratamiento", "Para la tos")
            PendingIntent.getBroadcast(context,0,intent, 0)

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
        /*
        alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 1,
                alarmItent
        )
        */
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

    private inner class ScheduleAlarmsForShotsNotificationsAsyncTask : AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {

            return null
        }
    }
}