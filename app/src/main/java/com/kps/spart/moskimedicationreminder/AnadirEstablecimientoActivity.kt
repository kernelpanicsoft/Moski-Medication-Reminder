package com.kps.spart.moskimedicationreminder

import Elementos.Establecimiento
import android.content.ContentValues
import android.content.Intent
import android.support.v4.app.NavUtils
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import model.mmrbd
import kotlinx.android.synthetic.main.activity_anadir_establecimiento.*
import model.MMDContract


class AnadirEstablecimientoActivity : AppCompatActivity() {

    lateinit var dbHelper : mmrbd
    lateinit var establecimiento: Establecimiento


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_establecimiento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.anadir_establecimiento)

        dbHelper = mmrbd(this@AnadirEstablecimientoActivity)

        establecimiento = Establecimiento()
        establecimiento.nombre

        SpinnerTipoEstablecimiento.adapter = ArrayAdapter(this@AnadirEstablecimientoActivity, android.R.layout.simple_spinner_dropdown_item,this.resources.getStringArray(R.array.tipo_establecimiento))
        SpinnerTipoEstablecimiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                establecimiento.tipo = parent?.getItemAtPosition(position).toString()
            }
        }
        anadirLocationButton.setOnClickListener{
            val nav = Intent(this@AnadirEstablecimientoActivity,MapsActivity::class.java)
            startActivity(nav)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSave -> {
                establecimiento.nombre  = NombreEstablecimientoTV.text.toString()
                establecimiento.direccion = DireccionEstablecimientoTV.text.toString()
                establecimiento.telefono1 = Telefono1EstablecimientoTV.text.toString()
                establecimiento.telefono2 = Telefono2EstablecimientoTV.text.toString()
                establecimiento.email = EmailEstablecimientoTV.text.toString()
                establecimiento.sitioWeb = SitioWebEstablecimeintoTV.text.toString()

                finish()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }


    private fun saveEstablishmentToDB(establecimiento: Establecimiento){
        val db = dbHelper.writableDatabase
        val errorAtInsertion : Long = -1

        val newRowId = db.insert(MMDContract.columnas.TABLA_ESTABLECIMIENTO,null,establecimiento.toContentValues())

        if(newRowId == errorAtInsertion){
            Toast.makeText(this@AnadirEstablecimientoActivity,getString(R.string.error_crear_establecimiento), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@AnadirEstablecimientoActivity, getString(R.string.establecimiento_creado_correctamente), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy(){
        dbHelper.close()
        super.onDestroy()
    }
}
