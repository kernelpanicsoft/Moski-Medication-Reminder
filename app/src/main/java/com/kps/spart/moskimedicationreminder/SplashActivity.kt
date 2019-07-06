package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val restartShots = Intent(this,MMRReiniciarDiaService::class.java)
        this.startService(restartShots)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        startActivity(Intent (this@SplashActivity,MainActivity::class.java))
        finish()
    }
}