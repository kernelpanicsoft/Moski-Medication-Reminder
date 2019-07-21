package alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.lang.StringBuilder

class TreatmentBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val idToma = intent.getStringExtra("TomaID")
        val cadena = intent.getStringExtra("Toma")
        Log.d("EstasReciviendo", idToma + " " + cadena)
    }
}