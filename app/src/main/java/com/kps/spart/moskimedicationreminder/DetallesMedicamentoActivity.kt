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
import kotlinx.android.synthetic.main.activity_detalles_medicamento.*
import model.MMDContract
import model.mmrbd

class DetallesMedicamentoActivity : AppCompatActivity() {
    private var medicine_id : Int = -1
    private lateinit var dbHelper: mmrbd
    private var iconsCollection: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_medicamento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detalles_medicamento)


        medicine_id = intent.getIntExtra("MEDICINE_ID", -1)
        iconsCollection = this@DetallesMedicamentoActivity.resources?.getStringArray(R.array.TipoMedicamento)
        populateMedicineFieldsFromDB()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_item -> {
                val builder = AlertDialog.Builder(this@DetallesMedicamentoActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    dialog, which ->
                    when(which){
                        0 -> {
                            val nav = Intent(this@DetallesMedicamentoActivity, AnadirMedicamentoActivity::class.java)
                            nav.putExtra("MEDICINE_ID", medicine_id)
                            startActivityForResult(nav, 417)
                        }
                        1 -> {
                            val innerBuilder = AlertDialog.Builder(this@DetallesMedicamentoActivity)
                            innerBuilder.setTitle(getString(R.string.eliminar_medicamento))
                                    .setMessage(getString(R.string.esta_seguro_que_desea_eliminar_el_medicamento))
                                    .setPositiveButton(getString(R.string.si)){
                                        dialog, id ->
                                        deleteMedicine()
                                    }
                                    .setNegativeButton(getString(R.string.no)){ dialog, id ->

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
            android.R.id.home -> {
                onBackPressed()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun populateMedicineFieldsFromDB(){
        dbHelper = mmrbd(this@DetallesMedicamentoActivity)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(MMDContract.columnas.NOMBRE_COMERCIAL_MEDICAMENTO,
                                MMDContract.columnas.NOMBRE_GENERICO_MEDICAMENTO,
                                MMDContract.columnas.DOSIS_MEDICAMENTO,
                                MMDContract.columnas.TIPO_MEDICAMENTO,
                                MMDContract.columnas.COLOR_MEDICAMENTO,
                                MMDContract.columnas.NOTA_MEDICAMENTO,
                                MMDContract.columnas.FOTOGRAFIA_MEDICAMENTO
        )

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$medicine_id")
        val cursor = db.query(
                MMDContract.columnas.TABLA_MEDICAMENTO,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                "1"
        )

        if(cursor.moveToFirst()){
            nombreComercialMedicamentoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_COMERCIAL_MEDICAMENTO))
            nombreGenericoMedicamentoTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_GENERICO_MEDICAMENTO))
            dosisMedicamento.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.DOSIS_MEDICAMENTO))
            val medicineType = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.TIPO_MEDICAMENTO))

            when(iconsCollection?.indexOf(medicineType)){
                0 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_roundpill)}
                1 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_tab)}
                2 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_capsula)}
                3 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_syrup)}
                4 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_drops)}
                5 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_eyedrops)}
                6 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_ointment)}
                7 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_powder)}
                8 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_gel)}
                9 -> {fabIconoMedicamento.setImageResource(R.drawable.ic_inhalator)}
                10-> {fabIconoMedicamento.setImageResource(R.drawable.ic_suppository)}
                11-> {fabIconoMedicamento.setImageResource(R.drawable.ic_intravenous)}
                12-> {fabIconoMedicamento.setImageResource(R.drawable.ic_syringe)}
            }

            fabIconoMedicamento.setColorFilter(cursor.getInt(cursor.getColumnIndexOrThrow(MMDContract.columnas.COLOR_MEDICAMENTO)))

            contenidoNotaTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOTA_MEDICAMENTO))

        }
    }

    private fun deleteMedicine(){
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$medicine_id")
        val deletedRows = db.delete(MMDContract.columnas.TABLA_MEDICAMENTO, selection,selectionArgs)

        if(deletedRows == 1){
            Toast.makeText(this@DetallesMedicamentoActivity,getString(R.string.medicamento_eliminado_correctamente), Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@DetallesMedicamentoActivity,getString(R.string.no_es_posible_eliminar_medicamento), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
