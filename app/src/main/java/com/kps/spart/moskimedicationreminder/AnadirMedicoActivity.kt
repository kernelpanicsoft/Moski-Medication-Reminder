package com.kps.spart.moskimedicationreminder

import MMR.viewModels.FichaContactoViewModel
import MMR.viewModels.MedicoViewModel
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import elements.FichaContacto
import elements.Medico
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_anadir_medico.*


class AnadirMedicoActivity : AppCompatActivity() {

   // private lateinit var medico : Medico

    lateinit var medicoViewModel : MedicoViewModel
    lateinit var medicoActualLive : LiveData<Medico>

    var tituloDR : String = ""
    var especialidadDR : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medico)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        medicoViewModel = ViewModelProviders.of(this@AnadirMedicoActivity).get(MedicoViewModel::class.java)
       // medico = Medico(0)

        if(intent.hasExtra("MEDIC_ID")){
            title = getString(R.string.editar_medico)
            medicoActualLive = medicoViewModel.getMedico(intent.getIntExtra("MEDIC_ID", -1))
            medicoActualLive.observe(this@AnadirMedicoActivity, android.arch.lifecycle.Observer {
                val tituloTypeIndex = this.resources.getStringArray(R.array.TituloMedico).indexOf(it?.titulo)
                spinnerTitulo.setSelection(tituloTypeIndex)

                val especialidadTypeIndex = this.resources.getStringArray(R.array.especialidades).indexOf(it?.especialidad)
                SpinnerEspecialidad.setSelection(especialidadTypeIndex)
                textInputLayoutNombre.setText(it?.nombre, TextView.BufferType.EDITABLE)

            })
        }else{
           title = getString(R.string.anadirMedico)
        }


        spinnerTitulo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TituloMedico))
        spinnerTitulo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tituloDR = parent?.getItemAtPosition(position).toString()


            }
        }

        SpinnerEspecialidad.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.especialidades))
        SpinnerEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                especialidadDR = parent?.getItemAtPosition(position).toString()

                when(position){
                    0 -> { iconoMedicoIV.setImageResource(R.drawable.ic_doctor)}
                    1 -> { iconoMedicoIV.setImageResource(R.drawable.ic_alergologo)}
                    2 -> { iconoMedicoIV.setImageResource(R.drawable.ic_anestesiologo)}
                    3 -> { iconoMedicoIV.setImageResource(R.drawable.ic_angiologo)}
                    4 -> { iconoMedicoIV.setImageResource(R.drawable.ic_cardiologo)}
                    5 -> { iconoMedicoIV.setImageResource(R.drawable.ic_dermatologo)}
                    6 -> { iconoMedicoIV.setImageResource(R.drawable.ic_endocrinologo)}
                    7 -> { iconoMedicoIV.setImageResource(R.drawable.ic_fisioterapeuta)}
                    8 -> { iconoMedicoIV.setImageResource(R.drawable.ic_gastroenterologo)}
                    9 -> { iconoMedicoIV.setImageResource(R.drawable.ic_geriatra)}
                    10 -> { iconoMedicoIV.setImageResource(R.drawable.ic_ginecologo)}
                    11 -> { iconoMedicoIV.setImageResource(R.drawable.ic_hematologo)}
                    12 -> { iconoMedicoIV.setImageResource(R.drawable.ic_homeopata)}
                    13 -> { iconoMedicoIV.setImageResource(R.drawable.ic_infectologo)}
                    14 -> { iconoMedicoIV.setImageResource(R.drawable.ic_internista)}
                    15 -> { iconoMedicoIV.setImageResource(R.drawable.ic_inmunologo)}
                    16 -> { iconoMedicoIV.setImageResource(R.drawable.ic_nefrologo)}
                    17 -> { iconoMedicoIV.setImageResource(R.drawable.ic_neumologo)}
                    18 -> { iconoMedicoIV.setImageResource(R.drawable.ic_neurologo)}
                    19 -> { iconoMedicoIV.setImageResource(R.drawable.ic_nutriologo)}
                    20 -> { iconoMedicoIV.setImageResource(R.drawable.ic_oftalmologo)}
                    21 -> { iconoMedicoIV.setImageResource(R.drawable.ic_ortopedista)}
                    22 -> { iconoMedicoIV.setImageResource(R.drawable.ic_otorrinolaringologo)}
                    23 -> { iconoMedicoIV.setImageResource(R.drawable.ic_pediatra)}
                    24 -> { iconoMedicoIV.setImageResource(R.drawable.ic_psiquiatra)}
                    25 -> { iconoMedicoIV.setImageResource(R.drawable.ic_proctologo)}
                    26 -> { iconoMedicoIV.setImageResource(R.drawable.ic_radiologo)}
                    27 -> { iconoMedicoIV.setImageResource(R.drawable.ic_reumatologo)}
                    28 -> { iconoMedicoIV.setImageResource(R.drawable.ic_urologo)}
                    29 -> { iconoMedicoIV.setImageResource(R.drawable.ic_traumatologo)}

                }
            }
        }

        guardarMedicoFAB.setOnClickListener {
            val medico : Medico
            if(intent.hasExtra("MEDIC_ID")){
                medico = medicoActualLive.value!!
            }else{
                medico = Medico(0)
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirMedicoActivity)
                val usuarioID = sharedPref.getInt("actualUserID", -1)
                medico.usuarioID = usuarioID

            }
            medico.titulo = tituloDR
            medico.nombre = textInputLayoutNombre.text.toString()
            medico.especialidad = especialidadDR


            if(medico.usuarioID != -1){
               if(!medico.nombre.isNullOrEmpty()){
                   saveMedicToBD(medico)
               }else{
                   Snackbar.make(it,getString(R.string.es_necesario_especificar_nombre_medico),Snackbar.LENGTH_LONG).show()
               }


            }
        }

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

    private fun saveMedicToBD(medico: Medico){
        if(intent.hasExtra("MEDIC_ID")){
            medicoViewModel.update(medico)
        }else{
            medicoViewModel.insert(medico)
        }


        setResult(Activity.RESULT_OK)
        finish()
    }


}
