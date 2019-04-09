package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
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
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_anadir_medicamento.*
import model.CodigosDeSolicitud
import org.xdty.preference.colorpicker.ColorPickerDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AnadirMedicamentoActivity : AppCompatActivity() {

    lateinit var medicamentoViewModel: MedicamentoViewModel
    lateinit var medicamentoActualLive : LiveData<Medicamento>
    var mCurrentPhotoPath: String = ""
    var targetW: Int = 0
    var targetH: Int = 0

    var colorMedicamento: Int = 0
    var tipoMedicamento: String = ""
    var selectedColor = 0

    var mNombreComercial: String? = null
    var mNombreGenerico: String? = null
    var mDosis: String? = null
    var mNota: String? = null
    var mColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medicamento)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        selectedColor = ContextCompat.getColor(this@AnadirMedicamentoActivity, R.color.blueberry)
        medicamentoViewModel = ViewModelProviders.of(this@AnadirMedicamentoActivity).get(MedicamentoViewModel::class.java)


        if(intent.hasExtra("MEDICINE_ID")){
            title = getString(R.string.editar_medicamento)
            medicamentoActualLive = medicamentoViewModel.getMedicamento(intent.getIntExtra("MEDICINE_ID",-1))
            medicamentoActualLive.observe(this, android.arch.lifecycle.Observer {

                if(mNombreComercial != null){
                    CampoNombreComercial.setText(mNombreComercial, TextView.BufferType.EDITABLE)
                }else{
                    CampoNombreComercial.setText(it?.nombreMedicamento, TextView.BufferType.EDITABLE)
                }

                if(mNombreGenerico != null){
                    CampoNombreGenerico.setText(mNombreGenerico,TextView.BufferType.EDITABLE)
                }else{
                    CampoNombreGenerico.setText(it?.nombreGenerico,TextView.BufferType.EDITABLE)
                }

                if(mDosis != null){
                    CampoDosis.setText(mDosis, TextView.BufferType.EDITABLE)
                }else{
                    CampoDosis.setText(it?.dosis,TextView.BufferType.EDITABLE)
                }

                if(mNota != null){
                    CampoNota.setText(mNota, TextView.BufferType.EDITABLE)
                }else{
                    CampoNota.setText(it?.nota,TextView.BufferType.EDITABLE)
                }

                if(savedInstanceState == null) {
                    val MedicineTypeIndex =  this.resources.getStringArray(R.array.TipoMedicamento).indexOf(it!!.tipo)
                   SpinnerTipoMedicamento.setSelection(MedicineTypeIndex)
                }else{
                    val type = savedInstanceState.getInt("IndexTipo")
                    SpinnerTipoMedicamento.setSelection(type)
                }

                if(mColor != 0){
                    selectedColor = mColor
                }else{
                    selectedColor = it?.color!!
                }

                MedicamentoIconoTV.setColorFilter(selectedColor)

                mCurrentPhotoPath = it?.fotografia!!
                displayPic()

            })


        }else{
            title = getString(R.string.AnadirMedicamento)

        }



        SpinnerTipoMedicamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TipoMedicamento))
        SpinnerTipoMedicamento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                tipoMedicamento = parent?.getItemAtPosition(position).toString()
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


        colorMedicamento = selectedColor
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
               // medicamento.color = selectedColor
                //   Toast.makeText(this@AnadirMedicamentoActivity,"Color seleccionado: " + color + " Valor del recurso: "+ String.format("#%06x",(0xFFFFFF and selectedColor)), Toast.LENGTH_SHORT).show()
            }

            colorPickerDialog.show(fragmentManager,"color_picker_dialer")
        }

        anadirMedicamentoFAB.setOnClickListener {
            val medicamento : Medicamento
            var usuarioID = -1

            if(intent.hasExtra("MEDICINE_ID")) {
              //  Toast.makeText(this@AnadirMedicamentoActivity,"Valores del medicamento live dentro de fab: " + medicamentoActualLive.value?.nombreGenerico, Toast.LENGTH_SHORT).show()
                medicamento = medicamentoActualLive.value!!

            }else {
                medicamento = Medicamento(0)
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirMedicamentoActivity)
                usuarioID = sharedPref.getInt("actualUserID", -1)
                medicamento.usuarioID = usuarioID
            }

                medicamento.nombreMedicamento = CampoNombreComercial.text.toString()
                medicamento.nombreGenerico = CampoNombreGenerico.text.toString()
                medicamento.dosis = CampoDosis.text.toString()
                medicamento.nota = CampoNota.text.toString()
                medicamento.color = selectedColor
                medicamento.tipo = tipoMedicamento
                medicamento.fotografia = mCurrentPhotoPath

                if(medicamento.usuarioID != -1){
                    if(!CampoNombreComercial.text.isEmpty() || !CampoNombreGenerico.text.isEmpty()){
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
                        if(!mCurrentPhotoPath.isEmpty()){
                            deleteImageFile()
                            mCurrentPhotoPath = ""
                            hideShowDeletePic(false)
                        }

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
                                    targetW = medicinePicCV.width
                                    targetH = medicinePicCV.height
                                    if(ContextCompat.checkSelfPermission(this@AnadirMedicamentoActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                        ActivityCompat.requestPermissions(this@AnadirMedicamentoActivity,
                                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                                CodigosDeSolicitud.SOLICITAR_PERMISO_ALMACENAMIENTO_EXTERNO)
                                    }else{
                                        if(!mCurrentPhotoPath.isEmpty()){
                                            deleteImageFile()
                                            mCurrentPhotoPath = ""
                                            imagenMedicamentoIV.setImageResource(R.drawable.no_photo_cardview)
                                        }
                                        pickFromGallery()
                                    }
                                }

                                1 -> {
                                    if(!mCurrentPhotoPath.isEmpty()){
                                        deleteImageFile()
                                        mCurrentPhotoPath = ""
                                        medicinePicCV.visibility = View.GONE
                                        eliminarImagenTV.visibility = View.GONE
                                    }
                                    targetW = medicinePicCV.width
                                    targetH = medicinePicCV.height
                                    dispatchTakePictureIntent()
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
        if(intent.hasExtra("MEDICINE_ID")){
            medicamentoViewModel.update(medicamento)
        }else{
            medicamentoViewModel.insert(medicamento)
        }
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
                "com.kps.spart.android.fileprovider",
                photoFile
        )

        this.contentResolver.delete(photoUri,null,null)
    }
    private fun setPic(){
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(mCurrentPhotoPath,this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor : Int = Math.min(photoW / targetW, photoH / resources.getDimension(R.dimen.altoFotoMedicamento).toInt())

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

    private fun displayPic(){

        BitmapFactory.decodeFile(mCurrentPhotoPath)?.also {scaledBitmap ->
            imagenMedicamentoIV.setImageBitmap(scaledBitmap)
            hideShowDeletePic(true)

        }
    }
    fun hideShowDeletePic( isTextViewDisplayed : Boolean){
        if(isTextViewDisplayed){
            eliminarImagenTV.visibility = View.VISIBLE
            medicinePicCV.visibility = View.VISIBLE
        }else{
            eliminarImagenTV.visibility = View.GONE
            imagenMedicamentoIV.setImageResource(R.drawable.no_photo_cardview)
        }
    }

    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.kps.spart.android.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent,CodigosDeSolicitud.ANADIR_FOTOGRAFIA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ANADIR_FOTOGRAFIA && resultCode == Activity.RESULT_OK){
            setPic()
            displayPic()
        }
        else if(requestCode == CodigosDeSolicitud.SELECCIONAR_IMAGEN && resultCode == Activity.RESULT_OK){
            val selectedImageUri = data?.data
            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = this.contentResolver.query(selectedImageUri, filePathColum, null, null, null)
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColum[0])
            val imgDecodableString = cursor.getString(columnIndex)
            cursor.close()

            val bmOptions = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
                BitmapFactory.decodeFile(imgDecodableString, this)
                val photoW: Int = outWidth
                val photoH: Int = outHeight

                val scaleFactor : Int = Math.min(photoW / targetW, photoH / resources.getDimension(R.dimen.altoFotoMedicamento).toInt())

                inJustDecodeBounds = false
                inSampleSize = scaleFactor
                inPurgeable = true
            }

            val imageFile : File? = try{
                createImageFile()
            }catch (ex : IOException){
                null
            }

            val out = FileOutputStream(imageFile)
            val exif = ExifInterface(imgDecodableString)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

            BitmapFactory.decodeFile(imgDecodableString, bmOptions).also { bitmap ->

                var rotatedBitmap : Bitmap? = null


                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        rotatedBitmap = rotateImage(bitmap,90f)
                    }
                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        rotatedBitmap = rotateImage(bitmap,180f)
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
                rotatedBitmap?.compress(Bitmap.CompressFormat.JPEG,85,out)
            }

            out.close()

            displayPic()
        }

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.run {
            putString("ActualPhotoPath", mCurrentPhotoPath)
            putInt("targetWidth", targetW)

            putString("NombreComercialActualizado", CampoNombreComercial.text.toString())
            putString("NombreGenericoActualizado", CampoNombreGenerico.text.toString())
            putString("DosisActualizado", CampoDosis.text.toString())
            putString("NotaActualizado", CampoNota.text.toString())
            putInt("ColorActualizado", selectedColor)
            putInt("IndexTipo", SpinnerTipoMedicamento.selectedItemPosition)

       //     Toast.makeText(this@AnadirMedicamentoActivity,"Color en onSave " + selectedColor, Toast.LENGTH_SHORT).show()


        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.run {
            mCurrentPhotoPath = getString("ActualPhotoPath")
            targetW = getInt("targetWidth")
            if(!mCurrentPhotoPath.isEmpty()){
                displayPic()
            }

            mNombreComercial = getString("NombreComercialActualizado")
            mNombreGenerico = getString("NombreGenericoActualizado")
            mDosis = getString("DosisActualizado")
            mNota = getString("NotaActualizado")
            mColor = getInt("ColorActualizado")
         //   MedicamentoIconoTV.setColorFilter(mColor)
         //   Toast.makeText(this@AnadirMedicamentoActivity,"Color en onRestore " + mColor, Toast.LENGTH_SHORT).show()

        }
    }

    fun pickFromGallery(){
        Intent(Intent.ACTION_PICK).also {  selectedPictureIntent ->
            selectedPictureIntent.type = "image/*"
            val mimeTypes = arrayOf("image/jpg", "image/png")
            selectedPictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(selectedPictureIntent,CodigosDeSolicitud.SELECCIONAR_IMAGEN)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CodigosDeSolicitud.SOLICITAR_PERMISO_ALMACENAMIENTO_EXTERNO -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickFromGallery()
                }else{
                    Toast.makeText(this@AnadirMedicamentoActivity,getString(R.string.es_necesario_pemitir_permisos_multimedia), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
