package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalles_medico.*
import model.MMDContract
import model.mmrbd

class DetallesMedicoActivity : AppCompatActivity() {
    private var medic_id : Int = -1
    private lateinit var  dbHelper: mmrbd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_medico)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detalles_de_medico)

        medic_id = intent.getIntExtra("MEDIC_ID", -1)
        populateMedicFieldFromDB()
        populateMedicContactCards()

    }

    private fun populateMedicFieldFromDB() {
        dbHelper = mmrbd(this@DetallesMedicoActivity)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(MMDContract.columnas.NOMBRE_DOCTOR,
                                MMDContract.columnas.TITULO_DOCTOR,
                                MMDContract.columnas.ESPECIALIDAD_DOCTOR,
                                MMDContract.columnas.COLOR_DOCTOR)

        val selection ="${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$medic_id")
        val cursor = db.query(
                MMDContract.columnas.TABLA_DOCTOR,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                "1"
        )

        if(cursor.moveToFirst()){
            TituloDoctorTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TITULO_DOCTOR))
            NombreDoctorTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_DOCTOR))
            EspecialidadTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.ESPECIALIDAD_DOCTOR))
        }
    }

    private fun populateMedicContactCards(){
        RecViewFichasContactoMedico.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this@DetallesMedicoActivity,LinearLayoutManager.VERTICAL, false)
        RecViewFichasContactoMedico.layoutManager = mLayoutManager

        val dbHelper = mmrbd(this@DetallesMedicoActivity)
        val db = dbHelper.readableDatabase

        val selection = "${BaseColumns._ID} =?"
        val selectionArgs = arrayOf("$medic_id")


        val cursor = db.query(MMDContract.columnas.TABLA_FICHA_CONTACTO,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null)

        val adapter = FichasContactoAdapter(this@DetallesMedicoActivity,cursor)

        RecViewFichasContactoMedico.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        menuInflater.inflate(R.menu.menu_edit,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        when(item.itemId){
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesMedicoActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    dialog, which ->
                    when(which){
                        0 ->{
                            val nav = Intent(this@DetallesMedicoActivity, AnadirMedicoActivity::class.java)
                            nav.putExtra("MEDIC_ID", medic_id)
                            startActivityForResult(nav, 683)
                        }
                        1 ->{
                            val innerbuilder = AlertDialog.Builder(this@DetallesMedicoActivity)
                            innerbuilder.setTitle(getString(R.string.eliminar_medico))
                                    .setMessage(getString(R.string.esta_seguro_que_desea_eliminar_al_medico))
                                    .setPositiveButton(getString(R.string.si)){
                                        _, _ ->
                                        deleteMedic()
                                    }
                                    .setNegativeButton(getString(R.string.no)){
                                        _, _ ->

                                    }
                            val innerDialog = innerbuilder.create()
                            innerDialog.show()
                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteMedic(){
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$medic_id")
        val deletedRows = db.delete(MMDContract.columnas.TABLA_DOCTOR, selection, selectionArgs)

        if(deletedRows == 1){
            Toast.makeText(this@DetallesMedicoActivity,getString(R.string.medico_eliminado_correctamente),Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@DetallesMedicoActivity,getString(R.string.no_es_posible_eliminar_medico), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        dbHelper.close()
    }
}
