package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import MMR.viewModels.TratamientoViewModel
import android.app.Activity
import android.app.DatePickerDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import elements.Medicamento
import elements.Tratamiento
import kotlinx.android.synthetic.main.activity_anadir_tratamiento.*
import model.CodigosDeSolicitud
import model.ContinuidadTratamiento
import model.TipoRecordatorio
import java.text.SimpleDateFormat
import java.util.*

class AnadirTratamientoActivity : AppCompatActivity() {
    lateinit var tratamientoViewModel: TratamientoViewModel
    lateinit var tratamientoActualLive: LiveData<Tratamiento>

    lateinit var medicamentoViewModel: MedicamentoViewModel
    lateinit var medicamentoActualLive: LiveData<Medicamento>
    var medicamentoID = -1
    var fecha_tratamiento = ""
    var calendario = Calendar.getInstance()
    var calendarioAux = Calendar.getInstance()
    val sdf = SimpleDateFormat.getDateInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_tratamiento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        tratamientoViewModel = ViewModelProviders.of(this@AnadirTratamientoActivity).get(TratamientoViewModel::class.java)
        medicamentoViewModel = ViewModelProviders.of(this@AnadirTratamientoActivity).get(MedicamentoViewModel::class.java)


        if(intent.hasExtra("TREATMENT_ID")){
            title = getString(R.string.editar_tratamiento)

            registrarTratamientoButton.text = getString(R.string.guardar_cambios_tratamiento)
            tratamientoActualLive = tratamientoViewModel.getTratamiento(intent.getIntExtra("TREATMENT_ID", -1))
            tratamientoActualLive.observe(this@AnadirTratamientoActivity, android.arch.lifecycle.Observer {tratamiento ->
                tituloTratamientoET.setText(tratamiento?.titulo, TextView.BufferType.EDITABLE)
                notaTratamientoET.setText(tratamiento?.indicaciones, TextView.BufferType.EDITABLE)
                populateSelectedMedicineCard(tratamiento?.medicamentoID)

                when(tratamiento?.recordatorio){
                    TipoRecordatorio.NOTIFICACION -> radioGroup.check(R.id.notificacionRadioButton)
                    TipoRecordatorio.ALARMA -> radioGroup.check(R.id.alarmaRadioButton)
                    TipoRecordatorio.NINGUNO -> radioGroup.check(R.id.ningunoRadioButton)
                }

                calendario.time = sdf.parse(tratamiento?.fechaInicio)
                FechaInicioButton.text = sdf.format(calendario.time)

                if(tratamiento?.diasTratamiento == -1){
                    spinnerPeriodicidad.setSelection(1)
                }else{
                    spinnerPeriodicidad.setSelection(0)
                    numberPicker.value = tratamiento?.diasTratamiento!!
                }

            })

        }else{
            title = getString(R.string.anadirTratamiento)
        }

        elegirMedicamentoBT.setOnClickListener {
            val selectMedicineIntent = Intent(this@AnadirTratamientoActivity,ElegirMedicamentoActivity::class.java)
            startActivityForResult(selectMedicineIntent,CodigosDeSolicitud.ELEGIR_MEDICAMENTO)
        }

        FechaInicioButton.text = sdf.format(calendario.time)
        FechaInicioButton.setOnClickListener {
            val datePickerFragment = DatePickerDialog(this@AnadirTratamientoActivity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendario.set(Calendar.YEAR, year)
                calendario.set(Calendar.MONTH, month)
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                FechaInicioButton.text = sdf.format(calendario.time)
                //fechaFinTratamientoTV.text = sdf.format(calendario.time)
                createDataBaseOnPicker(calendario)
                calendarioAux = calendario
            }, calendario.get(Calendar.YEAR),calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH))
            datePickerFragment.show()
        }

        spinnerPeriodicidad.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.continuidad_tratamiento))
        spinnerPeriodicidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

              //  Toast.makeText(this@AnadirTratamientoActivity,"Elemento: " + parent?.getItemAtPosition(position), Toast.LENGTH_SHORT).show()
                when(position){
                    //Periodo elegido
                    0->{
                        numeroDiasTV.visibility = View.VISIBLE
                      //  diasTratamientoET.visibility = View.VISIBLE
                        numeroDiasTV.visibility = View.VISIBLE
                        FechaFinTV.visibility = View.VISIBLE
                        fechaFinTratamientoTV.visibility = View.VISIBLE

                        numeroDiasTV.visibility = View.VISIBLE
                        numberPicker.visibility = View.VISIBLE
                    }
                    //Indefinido elegido
                    1->{
                        numeroDiasTV.visibility = View.GONE
                      //  diasTratamientoET.visibility = View.GONE
                        numeroDiasTV.visibility = View.GONE
                        FechaFinTV.visibility = View.GONE
                        fechaFinTratamientoTV.visibility = View.GONE

                        numeroDiasTV.visibility = View.GONE
                        numberPicker.visibility = View.GONE
                    }
                }
            }
        }

        numberPicker.minValue = 1
        numberPicker.maxValue = 100
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            calendarioAux = Calendar.getInstance()
            calendarioAux.set(calendario.get(Calendar.YEAR),calendario.get(Calendar.MONTH),calendario.get(Calendar.DATE))
            calendarioAux.add(Calendar.DAY_OF_MONTH,newVal)
            fechaFinTratamientoTV.text = sdf.format(calendarioAux.time)
        }

        createDataBaseOnPicker(Calendar.getInstance())

        registrarTratamientoButton.setOnClickListener {
           // val nav = Intent(this@AnadirTratamientoActivity,AnadirTomasActivity::class.java)
           // startActivity(nav)
            val tratamiento : Tratamiento
            var usuarioID = -1

            if(intent.hasExtra("TREATMENT_ID")){
                tratamiento = tratamientoActualLive.value!!
            }else{
                tratamiento = Tratamiento(0)
                val shaeredPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirTratamientoActivity)
                usuarioID = shaeredPref.getInt("actualUserID", -1)
                tratamiento.usuarioID = usuarioID
            }

            tratamiento.titulo = tituloTratamientoET.text.toString()
            tratamiento.medicamentoID = medicamentoID
            tratamiento.indicaciones = notaTratamientoET.text.toString()

            val selectedRadioButton : RadioButton = findViewById(radioGroup.checkedRadioButtonId)

            tratamiento.recordatorio = radioGroup.indexOfChild(selectedRadioButton)
            tratamiento.fechaInicio = FechaInicioButton.text.toString()


            if(spinnerPeriodicidad.selectedItemPosition == 0){
                tratamiento.fechaFin = fechaFinTratamientoTV.text.toString()
                tratamiento.diasTratamiento = numberPicker.value


            }else if(spinnerPeriodicidad.selectedItemPosition == 1){
                tratamiento.fechaFin = "INDEFINIDO"
                tratamiento.diasTratamiento = -1
            }
            saveTreatmentToDB(tratamiento)
        }
    }

    private fun createDataBaseOnPicker(cal: Calendar){

        val calendarioAux2 = Calendar.getInstance()
        calendarioAux2.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE))
        calendarioAux2.add(Calendar.DAY_OF_MONTH,numberPicker.value)
        Log.d("PICKER",numberPicker.value.toString())
        fechaFinTratamientoTV.text = sdf.format(calendarioAux2.time)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    fun saveTreatmentToDB(tratamiento : Tratamiento){
        if(intent.hasExtra("TREATMENT_ID")){
            tratamientoViewModel.update(tratamiento)
        }else{
            tratamientoViewModel.insert(tratamiento)
        }
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ELEGIR_MEDICAMENTO){
            if(resultCode == Activity.RESULT_OK){
                populateSelectedMedicineCard(data?.getIntExtra("SELECTED_MEDICINE_ID",-1))
            }
        }
    }

    fun populateSelectedMedicineCard(medicineID : Int?){
        medicineID?.run {
            medicamentoViewModel.getMedicamento(medicineID).observe(this@AnadirTratamientoActivity, Observer {
                mensajeMedicamentoTratamientoTV.visibility = View.GONE

                nombreComercialMedicamento.visibility = View.VISIBLE
                nombreMedicamento.visibility = View.VISIBLE
                MedicamentoIconoTV.visibility = View.VISIBLE
                nombreComercialMedicamento.setText(it?.nombreMedicamento,TextView.BufferType.EDITABLE)
                nombreMedicamento.setText(it?.nombreGenerico,TextView.BufferType.EDITABLE)
                MedicamentoIconoTV.setColorFilter(it?.color!!)

                val medicineType = it.tipo
                val iconsCollection = resources?.getStringArray(R.array.TipoMedicamento)
                when(iconsCollection?.indexOf(medicineType)){
                    0 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_roundpill)}
                    1 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_tab)}
                    2 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_capsula)}
                    3 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_syrup)}
                    4 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_drops)}
                    5 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_eyedrops)}
                    6 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_ointment)}
                    7 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_powder)}
                    8 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_gel)}
                    9 -> {MedicamentoIconoTV.setImageResource(R.drawable.ic_inhalator)}
                    10-> {MedicamentoIconoTV.setImageResource(R.drawable.ic_suppository)}
                    11-> {MedicamentoIconoTV.setImageResource(R.drawable.ic_intravenous)}
                    12-> {MedicamentoIconoTV.setImageResource(R.drawable.ic_syringe)}
                }

                medicamentoID = it.id
            })
        }
    }
}
