package com.kps.spart.moskimedicationreminder

import android.app.IntentService
import android.content.Intent
import android.widget.Toast

class MMRReiniciarDiaService : IntentService("MMRReiniciarDiaService"){

    override fun onHandleIntent(intent: Intent?) {
        try{
            Thread.sleep(5000)
        }catch (e : InterruptedException){
            Thread.currentThread().interrupt()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Toast.makeText(this,"Servicio finalizado", Toast.LENGTH_SHORT).show()
    }

}