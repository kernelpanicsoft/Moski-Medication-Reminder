package com.kps.spart.moskimedicationreminder

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
import android.widget.Button
import android.widget.Toast
import model.mmrbd
import kotlinx.android.synthetic.main.activity_anadir_farmacia.*
import model.MMDContract


class AnadirFarmaciaActivity : AppCompatActivity() {

    val dbHelper = mmrbd(this)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_farmacia)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.anadirFarmacia)

        val dbHelper = mmrbd(this)



        val buttonLocation = anadirLocationButton
        anadirLocationButton.setOnClickListener(View.OnClickListener {

            val nav = Intent(this@AnadirFarmaciaActivity,MapsActivity::class.java)
            startActivity(nav)

        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSave -> {
                Toast.makeText(this," " + savePharmacyToBD() ,Toast.LENGTH_SHORT).show()
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

    fun savePharmacyToBD() : Long?{
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(MMDContract.columnas.NOMBRE_FARMACIA, editText5.text.toString())
            put(MMDContract.columnas.DIRECCION_FARMACIA,editText6.text.toString())
            put(MMDContract.columnas.TELEFONO1_FARMACIA,editText7.text.toString())
            put(MMDContract.columnas.TELEFONO2_FARMACIA,editText8.text.toString())
            put(MMDContract.columnas.EMAIL_FARMACIA,editText9.text.toString())
            put(MMDContract.columnas.WEB_FARMACIA,editText10.text.toString())

        }

        val newRowID = db?.insert(MMDContract.columnas.TABLA_FARMACIA,null,values)


        return newRowID
    }

    override fun onDestroy(){
        dbHelper.close()
        super.onDestroy()
    }
}
