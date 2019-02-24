package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import elements.Medicamento
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_anadir_medicamento.*
import org.xdty.preference.colorpicker.ColorPickerDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class AnadirMedicamentoActivity : AppCompatActivity() {

    lateinit var medicamentoViewModel: MedicamentoViewModel
    lateinit var medicamento: Medicamento
    var mCurrentPhotoPath: String = ""
    var targetW: Int = 0
    var targetH: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medicamento)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.AnadirMedicamento)

        medicamentoViewModel = ViewModelProviders.of(this@AnadirMedicamentoActivity).get(MedicamentoViewModel::class.java)
        medicamento = Medicamento(0)

        SpinnerTipoMedicamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TipoMedicamento))
        SpinnerTipoMedicamento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                medicamento.tipo = parent?.getItemAtPosition(position).toString()
                //Toast.makeText(this@AnadirMedicamentoActivity, "Elemento seleccionado: " + position.toString() + " : " + parent?.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                when (position){
                    0 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_roundpill) }
                    1 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_tab)}
                    2 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_capsula)}
                    3 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_syrup)}
                    4 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_drops)}
                    5 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_eyedrops)}
                    6 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_ointment)}
                    7 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_powder)}
                    8 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_gel)}
                    9 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_inhalator)}
                    10-> { MedicamentoIconoTV.setImageResource(R.drawable.ic_suppository)}
                    11-> { MedicamentoIconoTV.setImageResource(R.drawable.ic_intravenous)}
                    12-> { MedicamentoIconoTV.setImageResource(R.drawable.ic_syringe)}
                }
            }
        }

        var selectedColor = ContextCompat.getColor(this@AnadirMedicamentoActivity,R.color.blueberry)
        medicamento.color = selectedColor
        val colors = resources.getIntArray(R.array.default_rainbow)

        MedicamentoIconoTV.setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.newInstance(R.string.colorDistintivo,
                    colors,
                    selectedColor,
                    5,
                    ColorPickerDialog.SIZE_SMALL,
                    true
            )

            colorPickerDialog.setOnColorSelectedListener { color ->
                selectedColor = color
                MedicamentoIconoTV.setColorFilter(selectedColor)
                medicamento.color = selectedColor

                //   Toast.makeText(this@AnadirMedicamentoActivity,"Color seleccionado: " + color + " Valor del recurso: "+ String.format("#%06x",(0xFFFFFF and selectedColor)), Toast.LENGTH_SHORT).show()
            }

            colorPickerDialog.show(fragmentManager,"color_picker_dialer")
        }


        anadirMedicamentoFAB.setOnClickListener {

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirMedicamentoActivity)
            val usuarioID = sharedPref.getInt("actualUserID", -1)
            medicamento.nombreMedicamento = CampoNombreComercial.text.toString()
            medicamento.nombreGenerico = CampoNombreGenerico.text.toString()
            medicamento.dosis = CampoDosis.text.toString()
            medicamento.nota = CampoNota.text.toString()

            if(usuarioID != -1){

                if(!CampoNombreComercial.text.isEmpty() || !CampoNombreGenerico.text.isEmpty()){
                    medicamento.usuarioID = usuarioID
                    saveMedicineToDB(medicamento)
                }else{

                    Snackbar.make(it,getString(R.string.es_necesario_especificar_nombre_comercial_generico), Snackbar.LENGTH_LONG).show()
                }

            }
        }

        eliminarImagenTV.setOnClickListener {
            val builder = AlertDialog.Builder(this@AnadirMedicamentoActivity)
            builder.setTitle(R.string.eliminar_imagen_pregunta)
                    .setPositiveButton(R.string.eliminar){ dialog, which ->

                    }
                    .setNegativeButton(R.string.cancelar){ dialog, which ->

                    }

            val dialog = builder.create()
            dialog.show()
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_photo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_photo_item -> {
                val builder = AlertDialog.Builder(this@AnadirMedicamentoActivity)
                        .setTitle(getString(R.string.elegir_imagen_perfil_desde))
                        .setItems(R.array.origen_imagen){ dialog, which ->
                            when(which) {
                                0 -> {

                                }

                                1 -> {

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

    fun saveMedicineToDB(medicamento: Medicamento){
        medicamentoViewModel.insert(medicamento)
        setResult(Activity.RESULT_OK)
        finish()
    }


    @Throws(IOException::class)
    private fun createImageFile() : File{
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}",
                ".jpg",
                storageDir
        ).apply {
            mCurrentPhotoPath = absolutePath
        }

    }

    @Throws(IOException::class)
    private fun deleteImageFile(){
        val photoFile = File(mCurrentPhotoPath)
        val photoUri: Uri = FileProvider.getUriForFile(
                this,
                "com.kps,spart.android.fileprovider",
                photoFile
        )

        this.contentResolver.delete(photoUri, null, null)
    }

    private fun setPic(){
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(mCurrentPhotoPath,this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor : Int = Math.min(photoW / resources.getDimension(R.dimen.UserProfileImageSingle).toInt(), photoH / resources.getDimension(R.dimen.UserProfileImageSingle).toInt())

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }

        val exif = ExifInterface(mCurrentPhotoPath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED)

        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)?.also { bitmap ->

            var rotatedBitmap : Bitmap? = null
            when(orientation){
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    rotatedBitmap = rotateImage(bitmap, 90f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    rotatedBitmap = rotateImage(bitmap, 180f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    rotatedBitmap = rotateImage(bitmap, 270f)
                }
                ExifInterface.ORIENTATION_NORMAL -> {
                    rotatedBitmap = bitmap
                }
                else -> {
                    rotatedBitmap = bitmap
                }
            }

            rescaleImage(rotatedBitmap)
        }
    }

    fun rotateImage(source : Bitmap, angle : Float) : Bitmap{
        val matrix = Matrix()
        matrix.postRotate(angle)

        return Bitmap.createBitmap(source, 0,0, source.width, source.height,matrix, true)
    }

    @Throws(IOException::class)
    private fun rescaleImage(scaleBitmap : Bitmap){
        val photoFile = File(mCurrentPhotoPath)
        val out = FileOutputStream(photoFile)
        scaleBitmap.compress(Bitmap.CompressFormat.JPEG,85, out)
        out.close()
    }

    fun hideShowDeletePic( isTextViewDisplayed : Boolean){
        if(isTextViewDisplayed){

        }else{

        }
    }
}
