package com.kps.spart.moskimedicationreminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import model.NOTIFICACION_ID

class SoundAlarmReceiver : BroadcastReceiver() {

    lateinit var mRingtone : Ringtone

    override fun onReceive(context: Context, intent: Intent) {
        val idToma = intent.getIntExtra("TomaID", -1)


        val mNotifyManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyManager.cancel(NOTIFICACION_ID)

    }

}