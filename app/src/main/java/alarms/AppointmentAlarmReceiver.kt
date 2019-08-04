package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import model.TipoRecordatorio

class AppointmentAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val titulo = intent?.getStringExtra("Titulo")
        val doctor = intent?.getStringExtra("Doctor")
        val idCita = intent?.getIntExtra("IDCita", -1)
        val recordatorio = intent?.getIntExtra("Recordatorio", -1)
        val especialidad = intent?.getStringExtra("Especialidad")

        val notificationManager = NotificationsManager(context!!)

        if(recordatorio == TipoRecordatorio.ALARMA){
            notificationManager.sendNotificationForAppointment(titulo!!,doctor!!, especialidad!!, idCita!!)
            val mediaPlayer = MediaPlayer()
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            mediaPlayer.setDataSource(context!!,alarmSound)
            mediaPlayer.prepare()


            val handler = Handler()
            handler.postDelayed(Runnable {
                mediaPlayer.start()
            }, 5000)

            val timer = object : CountDownTimer(15000,5000){
                override fun onTick(millisUntilFinished: Long) {

                }
                override fun onFinish() {
                    if(mediaPlayer.isPlaying){
                        mediaPlayer.stop()
                        mediaPlayer.release()
                    }
                }
            }
            timer.start()

        }
        else if(recordatorio == TipoRecordatorio.NOTIFICACION){
           notificationManager.sendNotificationForAppointment(titulo!!,doctor!!, especialidad!!, idCita!!)
        }

        Log.d("AlarmaCitaRecibida", "Alarma para cita recibida"  + titulo + recordatorio)

    }
}