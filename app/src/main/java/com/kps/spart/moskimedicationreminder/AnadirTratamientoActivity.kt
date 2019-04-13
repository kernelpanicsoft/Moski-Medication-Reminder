package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TratamientoViewModel
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
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
import android.widget.Toast
import elements.Tratamiento
import kotlinx.android.synthetic.main.activity_anadir_tratamiento.*


class AnadirTratamientoActivity : AppCompatActivity() {
    lateinit var tratamientoViewModel: TratamientoViewModel
    lateinit var tratamientoActualLive: LiveData<Tratamiento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_tratamiento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.anadirTratamiento)

        tratamientoViewModel = ViewModelProviders.of(this@AnadirTratamientoActivity).get(TratamientoViewModel::class.java)


        chooseMedicamentoSpinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TipoMedicamento))
        chooseMedicamentoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
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
}
