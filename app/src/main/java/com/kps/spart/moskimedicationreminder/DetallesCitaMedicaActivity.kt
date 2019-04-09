package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import elements.CitaMedica
import kotlinx.android.synthetic.main.activity_detalles_cita_medica.*
import kotlinx.android.synthetic.main.activity_registrar_usuario.*
import model.CodigosDeSolicitud
import model.MMDContract
import java.util.*

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
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesCitaMedicaActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    _, which ->
                    when(which){
                        0 -> {
                            val nav = Intent(this@DetallesCitaMedicaActivity, AnadirCitaMedicaActivity::class.java)
                            nav.putExtra("CITA_ID", citaID)
                            startActivityForResult(nav, CodigosDeSolicitud.EDITAR_CITA)
                        }
                        1 -> {
                            val innerBuilder = AlertDialog.Builder(this@DetallesCitaMedicaActivity)
                            innerBuilder.setTitle(getString(R.string.eliminar_cita))
                                    .setMessage(getString(R.string.esta_seguro_que_eliminar_cita))
                                    .setPositiveButton(getString(R.string.si)){ dialog, id ->
                                        deleteCita()
                                    }
                                    .setNegativeButton(getString(R.string.no)){ dialog, id ->

                                    }
                            val innerDialog = innerBuilder.create()
                            innerDialog.show()
                        }
                    }
                }

                val dialog = builder.create()
                dialog.show()
                return true
            }

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

        if(cita.latitud?.compareTo(0.0) != 0 || cita.latitud?.compareTo(0.0) != 0){
            addMapFragment(cita.latitud!!,cita.longitud!!)
            abrirEnMapasTV.visibility = View.VISIBLE
            abrirEnMapasTV.setOnClickListener {
                val uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f(%s)", cita.latitud, cita.longitud, cita.titulo)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }else{
            abrirEnMapasTV.visibility = View.GONE
            mapaAnadido.visibility = View.GONE
        }
    }

    private fun addMapFragment(lat : Double, lng: Double){
        mapaAnadido.visibility = View.VISIBLE

        val fragmentTransaction = fragmentManager.beginTransaction()

        val mapFragment = MapFragment()
        fragmentTransaction.add(R.id.mapaAnadido, mapFragment)
        fragmentTransaction.commit()

        mapFragment.getMapAsync{
            val markerLocation = LatLng(lat,lng)
            it.addMarker(MarkerOptions().position(markerLocation))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation,15.0f))
            it.uiSettings.isScrollGesturesEnabled = false
        }
    }

    private fun deleteCita(){
        if(citaMedicaActualLive.hasObservers()){
            citaMedicaActualLive.removeObservers(this@DetallesCitaMedicaActivity)
            citaMedicaViewModel.delete(citaMedicaActualLive.value!!)
            finish()
        }
    }

}
