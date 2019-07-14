package com.kps.spart.moskimedicationreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.lang.StringBuilder

private const val TAG = "MyBroadcastReceiver"

class TreatmentBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("Action ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also {
                log ->
                Log.d(TAG, log)
            }
        }
        if(intent.action == "android.intent.action.BOOT_COMPLETED"){
            //
        }
        Toast.makeText(context,"Estas invocando al broadcast",Toast.LENGTH_SHORT).show()
    }
}