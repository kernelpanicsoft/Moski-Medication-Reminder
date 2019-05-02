package com.kps.spart.moskimedicationreminder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_anadir_tomas.*

class AnadirTomasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_tomas)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.registrar_toma)

        Toast.makeText(this@AnadirTomasActivity,"Valor del ID del elemento: " + intent.getLongExtra("TREATMENT_ID", -1), Toast.LENGTH_SHORT).show()


    }
}
