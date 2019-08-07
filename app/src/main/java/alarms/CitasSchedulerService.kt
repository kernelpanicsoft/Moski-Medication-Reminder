package alarms

import MMR.repositories.CitaMedicaRepository
import MMR.repositories.TomaRepository
import android.app.IntentService
import android.content.Intent

class CitasSchedulerService : IntentService("CitasSchedulerService"){
    override fun onHandleIntent(intent: Intent?) {
        val citaMedicaRepository = CitaMedicaRepository(application)

        citaMedicaRepository.scheduleAlarmsForAppointments()
    }
}