package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import MMR.viewModels.TratamientoViewModel
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import elements.Medicamento
import elements.Tratamiento
import kotlinx.android.synthetic.main.activity_anadir_tratamiento.*
import model.CodigosDeSolicitud


class AnadirTratamientoActivity : AppCompatActivity() {
    lateinit var tratamientoViewModel: TratamientoViewModel
    lateinit var tratamientoActualLive: LiveData<Tratamiento>

    lateinit var medicamentoViewModel: MedicamentoViewModel
    lateinit var medicamentoActualLive: LiveData<Medicamento>
    var medicamentoID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_tratamiento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.anadirTratamiento)

        tratamientoViewModel = ViewModelProviders.of(this@AnadirTratamientoActivity).get(TratamientoViewModel::class.java)
        medicamentoViewModel = ViewModelProviders.of(this@AnadirTratamientoActivity).get(MedicamentoViewModel::class.java)


        elegirMedicamentoBT.setOnClickListener {
            val selectMedicineIntent = Intent(this@AnadirTratamientoActivity,ElegirMedicamentoActivity::class.java)
            startActivityForResult(selectMedicineIntent,CodigosDeSolicitud.ELEGIR_MEDICAMENTO)
        }



        spinnerPeriodicidad.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.continuidad_tratamiento))
        spinnerPeriodicidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val tratamiento : Tratamiento
        var usuarioID = -1
        when (item.itemId) {
            R.id.itemSave -> {
                if(intent.hasExtra("TREAT_ID")){

                }else{


                }
                tratamiento = Tratamiento(0)
                val shaeredPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirTratamientoActivity)
                usuarioID = shaeredPref.getInt("actualUserID", -1)
                tratamiento.usuarioID = usuarioID
                tratamiento.titulo = tituloTratamientoET.text.toString()
                tratamiento.medicamentoID = 2
                tratamiento.indicaciones = notaTratamientoET.text.toString()

                saveTreatmentToDB(tratamiento)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    fun saveTreatmentToDB(tratamiento : Tratamiento){
        if(intent.hasExtra("TREAT_ID")){
            tratamientoViewModel.update(tratamiento)
        }else{
            tratamientoViewModel.insert(tratamiento)
            Toast.makeText(this,"Tratamiento insertado", Toast.LENGTH_SHORT).show()
        }
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ELEGIR_MEDICAMENTO){
            if(resultCode == Activity.RESULT_OK){
               // Toast.makeText(this,"ID del medicamento: " + data?.getIntExtra("SELECTED_MEDICINE_ID",-1),Toast.LENGTH_SHORT).show()
                populateSelectedMedicineCard(data?.getIntExtra("SELECTED_MEDICINE_ID",-1))
            }
        }
    }

    fun populateSelectedMedicineCard(medicineID : Int?){
        medicineID?.run {
            medicamentoViewModel.getMedicamento(medicineID).observe(this@AnadirTratamientoActivity, Observer {
                nombreComercialMedicamento.setText(it?.nombreMedicamento,TextView.BufferType.EDITABLE)
            })
        }
    }
}
