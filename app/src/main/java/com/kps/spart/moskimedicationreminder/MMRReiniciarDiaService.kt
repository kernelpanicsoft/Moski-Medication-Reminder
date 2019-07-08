package com.kps.spart.moskimedicationreminder

import MMR.repositories.TomaRepository
import MMR.repositories.TratamientoRepository
import android.app.IntentService
import android.content.Intent
import android.widget.Toast


class MMRReiniciarDiaService : IntentService("MMRReiniciarDiaService"){

    override fun onHandleIntent(intent: Intent?) {
        val tomaRepository = TomaRepository(application)
        tomaRepository.resetAllTomasStatus()

        val tratamientoRepository = TratamientoRepository(application)
        tratamientoRepository.decreaseTreatmentsOneDay()
        
    }

}