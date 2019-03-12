package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import elements.Medicamento
import kotlinx.android.synthetic.main.activity_detalles_medicamento.*
import model.CodigosDeSolicitud
import java.io.File


class DetallesMedicamentoActivity : AppCompatActivity() {
    private var medicine_id : Int = -1
    lateinit var medicamentoViewModel : MedicamentoViewModel
    lateinit var medicamentoActualLive : LiveData<Medicamento>
    var medicamento : Medicamento? = null

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

        medicamentoViewModel = ViewModelProviders.of(this).get(MedicamentoViewModel::class.java)

        medicamentoActualLive = medicamentoViewModel.getMedicamento(medicine_id)
        medicamentoActualLive.observe(this, Observer {
            medicamento = it
            populateMedicineFieldsFromDB(medicamento)
        })

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
                            startActivityForResult(nav, CodigosDeSolicitud.EDITAR_MEDICAMENTO)
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

    private fun populateMedicineFieldsFromDB(medicamento: Medicamento?){

            nombreComercialMedicamentoTV.text = medicamento?.nombreMedicamento
            nombreGenericoMedicamentoTV.text = medicamento?.nombreGenerico
            dosisMedicamento.text = medicamento?.dosis
            val medicineType = medicamento?.tipo

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

            fabIconoMedicamento.setColorFilter(medicamento?.color!!)

            contenidoNotaTV.text = medicamento.nota
            val  valueInPixels = resources.getDimension(R.dimen.UserProfileImageSingle)

            medicamento.fotografia?.also {
                setPic(it,valueInPixels.toInt(),valueInPixels.toInt())

            }

    }

    private fun deleteImageFile(picturePath : String){
        val photoFile = File(picturePath)
        val photoUri: Uri = FileProvider.getUriForFile(
                this,
                "com.kps.spart.android.fileprovider",
                photoFile
        )

        this.contentResolver.delete(photoUri,null, null)
    }

    private fun setPic(mCurrentPhotoPath : String, targetW: Int, targetH: Int){
        val bmpOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(mCurrentPhotoPath, this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }

        BitmapFactory.decodeFile(mCurrentPhotoPath, bmpOptions)?.also {bitmap ->
            imagenMedicamentoIV.setImageBitmap(bitmap)
        }
    }

    private fun deleteMedicine(){
        if(medicamentoActualLive.hasObservers()){
            medicamentoActualLive.value?.fotografia?.run{
                deleteImageFile(this)
            }
            medicamentoActualLive.removeObservers(this@DetallesMedicamentoActivity)
                medicamentoViewModel.delete(medicamentoActualLive.value!!)
                finish()
            }
    }



}
