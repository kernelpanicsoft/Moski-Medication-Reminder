package com.kps.spart.moskimedicationreminder

import MMR.viewModels.FichaContactoViewModel
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import elements.FichaContacto
import kotlinx.android.synthetic.main.activity_anadir_ficha_contacto.*


class AnadirFichaContactoActivity : AppCompatActivity() {
    lateinit var fichaContactoViewModel: FichaContactoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_ficha_contacto)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.anadir_ficha_contacto)

        fichaContactoViewModel = ViewModelProviders.of(this@AnadirFichaContactoActivity).get(FichaContactoViewModel::class.java)

        saveContactCardFAB.setOnClickListener {
            val ficha = FichaContacto(0)
            val medicID = intent.getIntExtra("MEDIC_ID", -1)
            ficha.medico_ficha_id = medicID
            ficha.titulo = textInputLayoutTituloficha.text.toString()
            ficha.direccion = textInputLayoutDireccion.text.toString()
            ficha.telefono = textInputLayoutTelefono.text.toString()
            ficha.celular = textInputLayoutCelular.text.toString()
            ficha.email = textInputLayoutEmail.text.toString()
            ficha.sitioweb = textInputLayoutWebSite.text.toString()


            if(!ficha.titulo.isNullOrEmpty() && ficha.medico_ficha_id != -1){
                saveContactCardToDB(ficha)
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
        fichaContactoViewModel.insert(fichaContacto)

        setResult(Activity.RESULT_OK)
        finish()
    }
}




