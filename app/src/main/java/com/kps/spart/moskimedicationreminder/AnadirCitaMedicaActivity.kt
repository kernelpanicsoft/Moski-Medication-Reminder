package com.kps.spart.moskimedicationreminder

import Elementos.CitaMedica
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_anadir_cita_medica.*
import model.MMDContract
import model.TipoRecordatorio
import model.mmrbd
import org.xdty.preference.colorpicker.ColorPickerDialog
import java.text.SimpleDateFormat
import java.util.*


class AnadirCitaMedicaActivity : AppCompatActivity() {

    val calendario = Calendar.getInstance()
    val sdf = SimpleDateFormat.getDateTimeInstance()

    lateinit var dbHelper : mmrbd
    lateinit var CitaMedica : CitaMedica


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_cita_medica)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.anadirCita)
        FechaYHoraEspecificadaTV.text = sdf.format(calendario.time)

        CitaMedica = CitaMedica();
        dbHelper = mmrbd(this@AnadirCitaMedicaActivity)


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

        RadioButtonGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio_notificacion ->{
                    CitaMedica.tipoRecordatorio = TipoRecordatorio.NOTIFICACION
                }
                R.id.radio_alarma ->{
                    CitaMedica.tipoRecordatorio = TipoRecordatorio.ALARMA
                }
                R.id.radio_ninguna ->{
                    CitaMedica.tipoRecordatorio = TipoRecordatorio.NINGUNO
                }
            }
        }

        var selectedColor = ContextCompat.getColor(this@AnadirCitaMedicaActivity,R.color.blueberry)
        CitaMedica.color = selectedColor.toString()

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

                CitaMedica.color = selectedColor.toString()

                //   Toast.makeText(this@AnadirMedicamentoActivity,"Color seleccionado: " + color + " Valor del recurso: "+ String.format("#%06x",(0xFFFFFF and selectedColor)), Toast.LENGTH_SHORT).show()
            }

            colorPickerDialog.show(fragmentManager,"color_picker_dialer")
        }



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSave -> {
                CitaMedica.titulo = TituloCitaET.text.toString()
                CitaMedica.doctor = NombreMedicoET.text.toString()
                CitaMedica.especialidad = EspecialidadCitaET.text.toString()
                CitaMedica.nota = editTextNota.text.toString()
                CitaMedica.fechaYhora = FechaYHoraEspecificadaTV.text.toString()
                CitaMedica.ubicacion = UbicacionET.text.toString()

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirCitaMedicaActivity)
                val usuarioID = sharedPref.getInt("actualUserID", -1)

                if(usuarioID != -1){
                    CitaMedica.usuarioID = usuarioID
                    saveAppointmentToDB(CitaMedica)
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
        val db = dbHelper.writableDatabase
        val errorAtInsertion : Long = -1

        val newRowId = db.insert(MMDContract.columnas.TABLA_CITA, null, cita.toContentValues())

        if(newRowId == errorAtInsertion){
            Toast.makeText(this@AnadirCitaMedicaActivity,getString(R.string.ocurrio_un_problema_durante_la_creacion_cita), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@AnadirCitaMedicaActivity,getString(R.string.cita_registrada_correctamente), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
