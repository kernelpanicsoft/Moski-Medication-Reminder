package alarms

import MMR.repositories.TomaRepository
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kps.spart.moskimedicationreminder.UpdateShootService
import model.NOTIFICACION_ID
import java.lang.StringBuilder


class TreatmentBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val idToma = intent.getIntExtra("TomaID", -1)
        val actionToma = intent.getIntExtra("AcctionToma", -1)
        Log.d("EstasReciviendo", idToma.toString() + " " + actionToma)

        val updateShotIntent = Intent(context, UpdateShootService::class.java)
        updateShotIntent.putExtra("tomaID", idToma)
        updateShotIntent.putExtra("tomaStatus", actionToma)
        context.startService(updateShotIntent)



        val mNotifyManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyManager.cancel(NOTIFICACION_ID)

    }
}