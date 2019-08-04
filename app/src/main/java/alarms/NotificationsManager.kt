package alarms

import android.app.*
import android.content.*
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.AsyncTask
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.kps.spart.moskimedicationreminder.DetallesCitaMedicaActivity
import com.kps.spart.moskimedicationreminder.MainActivity
import com.kps.spart.moskimedicationreminder.R
import model.*
import java.util.*

class NotificationsManager(val context: Context) {
    lateinit var mNotifyManager : NotificationManager
    lateinit var mRingtone : Ringtone


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

    fun sendNotification(title: String, content: String, tomaID: Int){
        val takeShotIntent = Intent(context, TreatmentBroadcastReceiver::class.java).apply {
            putExtra("TomaID", tomaID)
            putExtra("AcctionToma", EstatusToma.TOMADA)

        }
        val takeShotPendingIntent = PendingIntent.getBroadcast(context, AccionNotificacion.TOMAR, takeShotIntent, PendingIntent.FLAG_ONE_SHOT)

        val skipShotIntent = Intent(context, TreatmentBroadcastReceiver::class.java).apply {
            putExtra("TomaID", tomaID)
            putExtra("AcctionToma", EstatusToma.PASADA)

        }
        val skipShotPendingIntent = PendingIntent.getBroadcast(context, AccionNotificacion.SALTAR, skipShotIntent, PendingIntent.FLAG_ONE_SHOT)

        val postPoneShotIntent = Intent(context, TreatmentBroadcastReceiver::class.java).apply {
            putExtra("TomaID", tomaID)
            putExtra("AcctionToma", EstatusToma.POSPUESTA)
        }
        val postPoneShotPendingIntent = PendingIntent.getBroadcast(context, AccionNotificacion.POSPONER, postPoneShotIntent, PendingIntent.FLAG_ONE_SHOT)


        val notifyBuilder = getNotificationBuilder(title,content)
        notifyBuilder.addAction(R.drawable.ic_capsula, context.getString(R.string.tomar), takeShotPendingIntent)
        notifyBuilder.addAction(R.drawable.ic_capsula,context.getString(R.string.saltar), skipShotPendingIntent)
        notifyBuilder.addAction(R.drawable.ic_capsula,context.getString(R.string.posponer), postPoneShotPendingIntent)

        mNotifyManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyManager.notify(NOTIFICACION_ID, notifyBuilder.build())
    }

    fun sendNotificationForAppointment(title: String, doctor: String, especialidad: String, idCita: Int){
        val notificationIntent = Intent(context, DetallesCitaMedicaActivity::class.java)
        notificationIntent.putExtra("CITA_ID", idCita)
        val notificationPendingIntent = PendingIntent.getActivity(context, NOTIFICACION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notifyBuilder = NotificationCompat.Builder(context, CANAL_PRIMARIO_ID)
                .setContentTitle(title)
                .setContentText(especialidad + " " + doctor)
                .setSmallIcon(R.drawable.ic_notification_capsule)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)


        mNotifyManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyManager.notify(NOTIFICACION_ID, notifyBuilder.build())
    }

    fun sendNotificationForAlarm(title: String, content: String, tomaID: Int){

        val notifyBuilder = getNotificationBuilder(title,content)

        mNotifyManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyManager.notify(NOTIFICACION_ID, notifyBuilder.build())
    }


}