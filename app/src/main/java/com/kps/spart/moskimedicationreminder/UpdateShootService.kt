package com.kps.spart.moskimedicationreminder

import MMR.repositories.TomaRepository
import android.app.IntentService
import android.content.Intent
import android.util.Log

class UpdateShootService : IntentService("UpdateShootService") {
    override fun onHandleIntent(intent: Intent?) {
        val tomaRepository = TomaRepository(application)

        val tomaID = intent?.getIntExtra("tomaID", -1)
        val tomaStatus =  intent?.getIntExtra("tomaStatus", -1)

        if(tomaID != -1 && tomaStatus != -1){
            tomaRepository.updateTomaStatus(tomaID!!, tomaStatus!!)
        }

        Log.d("ServicioReiniciaTomas", "Toma actualizada notificacion: " + tomaID + " " + tomaStatus)

    }
}