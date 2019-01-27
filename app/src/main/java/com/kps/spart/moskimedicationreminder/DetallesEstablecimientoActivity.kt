package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalles_establecimiento.*
import model.MMDContract

class DetallesEstablecimientoActivity : AppCompatActivity() {
    private var establishment_id : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_establecimiento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detalles_establecimiento)


        establishment_id = intent.getIntExtra("ESTABLISHMENT_ID", -1)
        populateEstablishmentFieldsFromDB()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesEstablecimientoActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    _, which ->
                    when(which){
                        0 ->{
                            val nav = Intent(this@DetallesEstablecimientoActivity,AnadirEstablecimientoActivity::class.java)
                            nav.putExtra("ESTABLISHMENT_ID", establishment_id )
                            startActivityForResult(nav, 241)
                        }
                        1 ->{
                            val innerBuilder = AlertDialog.Builder(this@DetallesEstablecimientoActivity)
                            innerBuilder.setTitle(getString(R.string.eliminar_establecimiento))
                                    .setMessage(getString(R.string.esta_seguro_que_desea_eliminar_el_establecimiento))
                                    .setPositiveButton(getString(R.string.si)) { dialog, id ->
                                      deleteEstablishment()
                                    }
                                    .setNegativeButton(getString(R.string.no)){ dialog, id ->
                                        Toast.makeText(this@DetallesEstablecimientoActivity,"Estas no eliminas el establecimiento" + id,Toast.LENGTH_SHORT).show()
                                    }

                            val innerDialog = innerBuilder.create()
                            innerDialog.show()

                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()

                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun populateEstablishmentFieldsFromDB(){


/*
        if(cursor.moveToFirst()){
            nombreEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_ESTABLECIMIENTO))
            tipoEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TIPO_ESTABLECIMIENTO))
            direccionEstablecimietoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.DIRECCION_ESTABLECIMIENTO))
            telefono1EstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TELEFONO1_ESTABLECIMIENTO))
            telefono2EstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TELEFONO2_ESTABLECIMIENTO))
            emailEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.EMAIL_ESTABLECIMIENTO))
            sitioWebEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.WEB_ESTABLECIMIENTO))

        }
        */
    }

    private fun deleteEstablishment(){

    }



}
