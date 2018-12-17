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
import model.mmrbd

class DetallesEstablecimientoActivity : AppCompatActivity() {
    private var establishment_id : Int = -1
    private lateinit var dbHelper: mmrbd

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
        dbHelper = mmrbd(this@DetallesEstablecimientoActivity)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(MMDContract.columnas.NOMBRE_ESTABLECIMIENTO,
                                MMDContract.columnas.TIPO_ESTABLECIMIENTO,
                                MMDContract.columnas.DIRECCION_ESTABLECIMIENTO,
                                MMDContract.columnas.TELEFONO1_ESTABLECIMIENTO,
                                MMDContract.columnas.TELEFONO2_ESTABLECIMIENTO,
                                MMDContract.columnas.EMAIL_ESTABLECIMIENTO,
                                MMDContract.columnas.WEB_ESTABLECIMIENTO,
                                MMDContract.columnas.LATITUD_ESTABLECIMIENTO,
                                MMDContract.columnas.LONGITUD_ESTABLECIMIENTO)

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$establishment_id")
        val cursor = db.query(
                MMDContract.columnas.TABLA_ESTABLECIMIENTO,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                "1"
        )

        if(cursor.moveToFirst()){
            nombreEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_ESTABLECIMIENTO))
            tipoEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TIPO_ESTABLECIMIENTO))
            direccionEstablecimietoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.DIRECCION_ESTABLECIMIENTO))
            telefono1EstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TELEFONO1_ESTABLECIMIENTO))
            telefono2EstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TELEFONO2_ESTABLECIMIENTO))
            emailEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.EMAIL_ESTABLECIMIENTO))
            sitioWebEstablecimientoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.WEB_ESTABLECIMIENTO))

        }
    }

    private fun deleteEstablishment(){
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$establishment_id")
        val deletedRows = db.delete(MMDContract.columnas.TABLA_ESTABLECIMIENTO, selection,selectionArgs)

        if(deletedRows == 1){
            Toast.makeText(this@DetallesEstablecimientoActivity,getString(R.string.establecimiento_eliminado_correctamente), Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@DetallesEstablecimientoActivity,getString(R.string.no_es_posible_eliminar_establecimiento), Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
