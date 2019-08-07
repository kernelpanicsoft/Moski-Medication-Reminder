package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import alarms.NotificationsManager
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import model.CodigosDeSolicitud
import model.REGISTRO_PRIMER_USUARIO

class PantallaBienvenida : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)


        bienvenidaBT.setOnClickListener {
            val registerFirstUser = Intent(this, RegistrarUsuarioActivity::class.java)
            registerFirstUser.putExtra("PRIMER_USUARIO", REGISTRO_PRIMER_USUARIO)
            startActivityForResult(registerFirstUser,CodigosDeSolicitud.REGISTRAR_PRIMER_USUARIO)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CodigosDeSolicitud.REGISTRAR_PRIMER_USUARIO && resultCode == Activity.RESULT_OK){
            val usuarioViewModel  = ViewModelProviders.of(this).get(UsuarioViewModel::class.java)

            usuarioViewModel.getLastUserID().observe(this, Observer {
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
                with(sharedPref.edit()){
                    putInt("actualUserID",it!!.toInt())
                    apply()
                }

                finish()
            })
        }
    }


}
