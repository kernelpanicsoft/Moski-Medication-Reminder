package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import model.REGISTRO_PRIMER_USUARIO

class welcome_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        bienvenidaBT.setOnClickListener {
            val registerFirstUser = Intent(this, RegistrarUsuarioActivity::class.java)
            registerFirstUser.putExtra("PRIMER_USUARIO", REGISTRO_PRIMER_USUARIO)
            startActivity(registerFirstUser)
        }
    }
}
