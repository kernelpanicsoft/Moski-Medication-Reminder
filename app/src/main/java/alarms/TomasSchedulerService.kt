package alarms

import MMR.repositories.CitaMedicaRepository
import MMR.repositories.TomaRepository
import android.app.IntentService
import android.content.Intent

class TomasSchedulerService : IntentService("TomasSchedulerService") {
    override fun onHandleIntent(intent: Intent?) {
        val tomaRepository = TomaRepository(application)

        tomaRepository.scheduleAlarmsForShots()
    }
}