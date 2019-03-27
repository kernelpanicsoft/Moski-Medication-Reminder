package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import elements.CitaMedica
import kotlinx.android.synthetic.main.activity_detalles_cita_medica.*
import model.MMDContract

class DetallesCitaMedicaActivity : AppCompatActivity() {
    var citaID : Int = -1
    lateinit var citaMedicaViewModel: CitaMedicaViewModel
    lateinit var citaMedicaActualLive : LiveData<CitaMedica>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_cita_medica)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        val textViewTituloCita = findViewById<TextView>(R.id.tituloCitaMedica)

        textViewTituloCita.text = MMDContract.columnas.TABLA_DOCTOR

        title = getString(R.string.detalles_cita_medica)

        citaID = intent.getIntExtra("CITA_ID", -1)

        citaMedicaViewModel = ViewModelProviders.of(this).get(CitaMedicaViewModel::class.java)
        citaMedicaActualLive = citaMedicaViewModel.getCitaMedica(citaID)
        citaMedicaActualLive.observe(this, Observer {
            populateAppointmentFieldsFromDB(it)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.edit_item -> return true
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun populateAppointmentFieldsFromDB(cita : CitaMedica?){
            tituloCitaMedica.text = cita?.titulo
            nombreMedicoCitaTV.text = cita?.doctor
            fechaYHoraCitaTV.text = cita?.fechaYhora
            notasCitaTV.text = cita?.nota
            DireccionCitaTV.text = cita?.ubicacion
            iconoCitaMedica.setColorFilter(cita?.color!!)
    }

}
