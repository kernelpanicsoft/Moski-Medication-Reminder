package com.kps.spart.moskimedicationreminder

import MMR.viewModels.FichaContactoViewModel
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.widget.TextView
import elements.FichaContacto
import kotlinx.android.synthetic.main.activity_anadir_ficha_contacto.*


class AnadirFichaContactoActivity : AppCompatActivity() {
    lateinit var fichaContactoViewModel: FichaContactoViewModel
    lateinit var fichaContactoActualLive: LiveData<FichaContacto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_ficha_contacto)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        fichaContactoViewModel = ViewModelProviders.of(this@AnadirFichaContactoActivity).get(FichaContactoViewModel::class.java)


        if(intent.hasExtra("CARD_ID")){
            title = getString(R.string.editar_ficha_contacto)
            fichaContactoActualLive = fichaContactoViewModel.getFichaContacto(intent.getIntExtra("CARD_ID", -1))
            fichaContactoActualLive.observe(this, android.arch.lifecycle.Observer {

                textInputLayoutTituloficha.setText(it?.titulo, TextView.BufferType.EDITABLE)
                textInputLayoutDireccion.setText(it?.direccion, TextView.BufferType.EDITABLE)
                textInputLayoutTelefono.setText(it?.telefono, TextView.BufferType.EDITABLE)
                textInputLayoutCelular.setText(it?.celular, TextView.BufferType.EDITABLE)
                textInputLayoutEmail.setText(it?.email, TextView.BufferType.EDITABLE)
                textInputLayoutWebSite.setText(it?.sitioweb, TextView.BufferType.EDITABLE)

            })
        }else{
            title = getString(R.string.anadir_ficha_contacto)
        }

        saveContactCardFAB.setOnClickListener {
            val ficha : FichaContacto

            if(intent.hasExtra("CARD_ID")){
                ficha = fichaContactoActualLive.value!!
            }else{
                ficha = FichaContacto(0)
                val medicID = intent.getIntExtra("MEDIC_ID", -1)
                ficha.medico_ficha_id = medicID
            }

            ficha.titulo = textInputLayoutTituloficha.text.toString()
            ficha.direccion = textInputLayoutDireccion.text.toString()
            ficha.telefono = textInputLayoutTelefono.text.toString()
            ficha.celular = textInputLayoutCelular.text.toString()
            ficha.email = textInputLayoutEmail.text.toString()
            ficha.sitioweb = textInputLayoutWebSite.text.toString()


            if(!ficha.titulo.isNullOrEmpty() && ficha.medico_ficha_id != -1){
                saveContactCardToDB(ficha)
            }else{
                Snackbar.make(it,getString(R.string.es_necesario_especificar_el_titulo_de_la_ficha), Snackbar.LENGTH_LONG).show()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun saveContactCardToDB(fichaContacto : FichaContacto){
        if(intent.hasExtra("CARD_ID")){
            fichaContactoViewModel.update(fichaContacto)
        }else{
            fichaContactoViewModel.insert(fichaContacto)

        }

        setResult(Activity.RESULT_OK)
        finish()
    }
}




