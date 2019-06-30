package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
import android.app.Activity
import elements.CitaMedica
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_anadir_cita_medica.*


import model.TipoRecordatorio

import org.xdty.preference.colorpicker.ColorPickerDialog
import java.text.SimpleDateFormat
import java.util.*


class AnadirCitaMedicaActivity : AppCompatActivity() {
    lateinit var citaViewModel : CitaMedicaViewModel
    private lateinit var citaActualLive : LiveData<CitaMedica>

    var selectedColor: Int = 0

    var latitud : Double = 0.0
    var longitud : Double = 0.0

    val calendario = Calendar.getInstance()
    val sdf = SimpleDateFormat.getDateTimeInstance()


    var mTipoRecordatorio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_cita_medica)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        selectedColor = ContextCompat.getColor(this@AnadirCitaMedicaActivity,R.color.blueberry)
        citaViewModel = ViewModelProviders.of(this@AnadirCitaMedicaActivity).get(CitaMedicaViewModel::class.java)


        if(intent.hasExtra("CITA_ID")){
            title = getString(R.string.editar_cita)
            citaActualLive = citaViewModel.getCitaMedica(intent.getIntExtra("CITA_ID",-1))
            citaActualLive.observe(this@AnadirCitaMedicaActivity, android.arch.lifecycle.Observer {

                TituloCitaET.setText(it?.titulo, TextView.BufferType.EDITABLE)
                NombreMedicoET.setText(it?.doctor, TextView.BufferType.EDITABLE)
                EspecialidadCitaET.setText(it?.especialidad, TextView.BufferType.EDITABLE)
                NotaET.setText(it?.nota,TextView.BufferType.EDITABLE)
                FechaYHoraEspecificadaTV.text = it?.fechaYhora
                UbicacionET.setText(it?.ubicacion, TextView.BufferType.EDITABLE)

                when(it?.tipoRecordatorio){
                    TipoRecordatorio.NOTIFICACION ->{
                        AlertRadioButtonGroup.check(R.id.radio_notificacion)
                    }
                    TipoRecordatorio.ALARMA ->{
                        AlertRadioButtonGroup.check(R.id.radio_alarma)
                    }
                    TipoRecordatorio.NINGUNO ->{
                        AlertRadioButtonGroup.check(R.id.radio_ninguna)
                    }
                }

                selectedColor = it?.color!!
                iconoCita.setColorFilter(selectedColor)

                latitud = it?.latitud!!
                longitud = it?.longitud!!

                if(latitud != 0.0 && longitud != 0.0){
                    addMapFragment(latitud, longitud)
                    anadirUbicacionCitaButton.text = getString(R.string.eliminar_ubicacion)
                }
            })
        }else{
            title = getString(R.string.anadirCita)

            if(intent.hasExtra("nombreMedico")){
                val nombreMedico = intent.getStringExtra("tituloMedico") + " " + intent.getStringExtra("nombreMedico")
                val especialidadMedico = intent.getStringExtra("especialidadMedico")
                val direccionMedico = intent.getStringExtra("direccionMedico")

                NombreMedicoET.setText(nombreMedico, TextView.BufferType.EDITABLE)
                EspecialidadCitaET.setText(especialidadMedico, TextView.BufferType.EDITABLE)
                UbicacionET.setText(direccionMedico, TextView.BufferType.EDITABLE)
            }
        }

        FechaYHoraEspecificadaTV.text = sdf.format(calendario.time)


        FechaCitaButton.setOnClickListener{
            val datePickerFragment = DatePickerDialog(this@AnadirCitaMedicaActivity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendario.set(Calendar.YEAR, year)
                calendario.set(Calendar.MONTH, month)
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                //Toast.makeText(this@AnadirCitaMedicaActivity,"Fecha seleccionada: " + sdf.format(calendario.time), Toast.LENGTH_SHORT).show()
                FechaYHoraEspecificadaTV.text = sdf.format(calendario.time)
            }, calendario.get(Calendar.YEAR),calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH))
            datePickerFragment.show()

        }

        HoraCitaButton.setOnClickListener{
            val timePickerFragment = TimePickerDialog(this@AnadirCitaMedicaActivity, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                calendario.set(Calendar.HOUR_OF_DAY,hourOfDay)
                calendario.set(Calendar.MINUTE, minute)

                //Toast.makeText(this@AnadirCitaMedicaActivity,"Hora seleccionada: " + sdf.format(calendario.time),Toast.LENGTH_SHORT).show()
                FechaYHoraEspecificadaTV.text = sdf.format(calendario.time)
            }, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this@AnadirCitaMedicaActivity))

            timePickerFragment.show()

        }

        mTipoRecordatorio = TipoRecordatorio.NOTIFICACION
        AlertRadioButtonGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio_notificacion ->{
                    mTipoRecordatorio = TipoRecordatorio.NOTIFICACION
                }
                R.id.radio_alarma ->{
                    mTipoRecordatorio = TipoRecordatorio.ALARMA
                }
                R.id.radio_ninguna ->{
                    mTipoRecordatorio = TipoRecordatorio.NINGUNO
                }
            }
        }


    //    CitaMedica.color = selectedColor

        val colors = resources.getIntArray(R.array.default_rainbow)

        colorDistintivoButton.setOnClickListener{
            val colorPickerDialog = ColorPickerDialog.newInstance(R.string.colorDistintivo,
                    colors,
                    selectedColor,
                    5,
                    ColorPickerDialog.SIZE_SMALL,
                    true
            )

            colorPickerDialog.setOnColorSelectedListener { color ->
                selectedColor = color
                iconoCita.setColorFilter(selectedColor)

              //  CitaMedica.color = selectedColor

                //   Toast.makeText(this@AnadirMedicamentoActivity,"Color seleccionado: " + color + " Valor del recurso: "+ String.format("#%06x",(0xFFFFFF and selectedColor)), Toast.LENGTH_SHORT).show()
            }

            colorPickerDialog.show(fragmentManager,"color_picker_dialer")
        }

        anadirUbicacionCitaButton.setOnClickListener {
            if(latitud == 0.0 && longitud == 0.0){
                val nav = Intent(this@AnadirCitaMedicaActivity, MapsActivity::class.java)
                startActivityForResult(nav, 6832)
            }else{
                disableMapFragment()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 6832){
            if(resultCode == Activity.RESULT_OK){
                latitud = data?.getDoubleExtra("lat", 0.0)!!
                longitud = data.getDoubleExtra("lng",0.0)
                addMapFragment(latitud,longitud)
                anadirUbicacionCitaButton.text = getString(R.string.eliminar_ubicacion)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSave -> {
                val cita : CitaMedica
                var usuarioID = -1

                if(intent.hasExtra("CITA_ID")){
                    cita = citaActualLive.value!!
                }else{
                    cita = CitaMedica(0)
                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirCitaMedicaActivity)
                    usuarioID = sharedPref.getInt("actualUserID", -1)
                    cita.usuarioID = usuarioID
                }

                cita.titulo = TituloCitaET.text.toString()
                cita.doctor = NombreMedicoET.text.toString()
                cita.especialidad = EspecialidadCitaET.text.toString()
                cita.nota = NotaET.text.toString()
                cita.fechaYhora = FechaYHoraEspecificadaTV.text.toString()
                cita.ubicacion = UbicacionET.text.toString()
                cita.tipoRecordatorio = mTipoRecordatorio
                cita.color = selectedColor
                cita.latitud = latitud
                cita.longitud = longitud

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirCitaMedicaActivity)
                usuarioID = sharedPref.getInt("actualUserID", -1)

                if(usuarioID != -1 && !cita.titulo.isNullOrEmpty()){
                    saveAppointmentToDB(cita)
                }else{
                    Snackbar.make(TituloCitaET,getString(R.string.es_necesario_especificar_titulo_cita), Snackbar.LENGTH_LONG).show()
                }
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun saveAppointmentToDB(cita : CitaMedica){
        if(intent.hasExtra("CITA_ID")){
            citaViewModel.update(cita)
        }else{
            citaViewModel.insert(cita)
        }


        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun addMapFragment(lat : Double, lng : Double){
        mapaAnadido.visibility = View.VISIBLE
        val fragmentTransaction = fragmentManager.beginTransaction()
        val mapFragment = MapFragment()
        fragmentTransaction.add(R.id.mapaAnadido,mapFragment)
        fragmentTransaction.commit()

        mapFragment.getMapAsync{
            val marketLocation = LatLng(lat,lng)
            it.addMarker(MarkerOptions().position(marketLocation))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(marketLocation,15.0f))
            it.uiSettings.isScrollGesturesEnabled = false
        }
    }

    private fun disableMapFragment(){
        val mapFragment = fragmentManager.findFragmentById(R.id.mapaAnadido)
        if(mapFragment != null){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(mapFragment).commit()
        }

        mapaAnadido.visibility = View.GONE
        latitud = 0.0
        longitud = 0.0
        anadirUbicacionCitaButton.text = getString(R.string.a_adir_ubicaci_n_en_mapa)
    }

}
