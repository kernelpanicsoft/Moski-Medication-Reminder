package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.Toast
import model.TipoRecordatorio

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        val tratamiento = intent?.getStringExtra("Tratamiento")
        val medicamento = intent?.getStringExtra("Medicamento")
        val idToma = intent?.getIntExtra("IDToma", -1)
        val recordatorio = intent?.getIntExtra("Recordatorio", -1)

        val notificationManager = NotificationsManager(context!!)


        if(recordatorio == TipoRecordatorio.ALARMA){
            notificationManager.sendNotificationForAlarm(tratamiento!!,medicamento!!, idToma!!)
            val mediaPlayer = MediaPlayer()
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            mediaPlayer.setDataSource(context!!,alarmSound)
            mediaPlayer.prepare()


            val handler = Handler()
            handler.postDelayed(Runnable {
                mediaPlayer.start()
            }, 5000)

            val timer = object : CountDownTimer(10000,5000){
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

        }else if(recordatorio == TipoRecordatorio.NOTIFICACION){
            notificationManager.sendNotification(tratamiento!!,medicamento!!, idToma!!)
        }

        Log.d("Alarma","Se disparo: " + tratamiento + " | " + medicamento + " | " + idToma)

    }
}