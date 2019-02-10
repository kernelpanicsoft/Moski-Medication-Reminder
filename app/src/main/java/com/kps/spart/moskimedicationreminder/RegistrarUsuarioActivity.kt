package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import elements.Usuario
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registrar_usuario.*

import model.CodigosDeSolicitud
import model.MMDContract
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class RegistrarUsuarioActivity : AppCompatActivity() {

    lateinit var usuarioViewModel : UsuarioViewModel
    private lateinit var usuarioActualLive : LiveData<Usuario>
    var mCurrentPhotoPath : String = ""
    var targetW: Int = 0
    var targetH: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)


        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        usuarioViewModel = ViewModelProviders.of(this@RegistrarUsuarioActivity).get(UsuarioViewModel::class.java)

        if(intent.hasExtra("USER_ID")){
            title = getString(R.string.editar_usuario)
            usuarioActualLive = usuarioViewModel.getUsuario(intent.getIntExtra("USER_ID", -1))
            usuarioActualLive.observe(this, android.arch.lifecycle.Observer {
                nombreUsuarioET.setText(it?.nombre, TextView.BufferType.EDITABLE)
                apellidoUsuarioET.setText(it?.apellidos, TextView.BufferType.EDITABLE)
                Edad.setText(it?.edad.toString(),TextView.BufferType.EDITABLE)
                val genero = it?.genero

                if(genero!!.equals(getString(R.string.masculino))){
                    GeneroRadioGroup.check(R.id.masculinoRB)
                }else{
                    GeneroRadioGroup.check(R.id.femeninoRB)
                }

                if(!it?.password.isNullOrEmpty()){
                    usaPasswordCheckbox.isChecked = true
                }

                RecoveryET.setText(it?.email_recuperacion,TextView.BufferType.EDITABLE)
                PasswordEditText.setText(it?.password,TextView.BufferType.EDITABLE)
                mCurrentPhotoPath = it.imagen!!
                if(!mCurrentPhotoPath.isNullOrEmpty()){
                    val  valueInPixels = resources.getDimension(R.dimen.UserProfileImageSingle)
                    targetH = valueInPixels.toInt()
                    targetW = valueInPixels.toInt()
                    setPic()
                }
            })

        }else{
            title = getString(R.string.registrar_usuario)
        }


        iconoInfoTV.setOnClickListener{
            val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.Porque_usar_contrasena))
                        .setMessage(getString(R.string.explicacion_contrasena))
                        .setPositiveButton(getString(R.string.Entendido)) { dialog, which ->

                        }
            val dialog = builder.create()
            dialog.show()
        }


        usaPasswordCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                PasswordEditText.visibility = View.VISIBLE
                emailRecuperacionTV.visibility = View.VISIBLE
                RecoveryET.visibility = View.VISIBLE

            }else{
                PasswordEditText.visibility = View.GONE
                emailRecuperacionTV.visibility = View.GONE
                RecoveryET.visibility = View.GONE
                PasswordEditText.text.clear()
                RecoveryET.text.clear()
            }
        }

        savePerfilFAB.setOnClickListener{
            val usuario : Usuario
            if(intent.hasExtra("USER_ID")){
                 usuario = usuarioActualLive.value!!
            }else{
                 usuario = Usuario(0)
            }

            if(nombreUsuarioET.text.isEmpty() || apellidoUsuarioET.text.isEmpty() || Edad.text.isEmpty()){
                Snackbar.make(it,getString(R.string.nombre_apellido_necesario),Snackbar.LENGTH_LONG).show()
            }else{
                usuario.nombre = nombreUsuarioET.text.toString()
                usuario.apellidos = apellidoUsuarioET.text.toString()
                usuario.imagen = mCurrentPhotoPath

                try{
                    usuario.edad = Edad.text.toString().toInt()
                }catch( e : Exception){

                    Snackbar.make(it,getString(R.string.edad_necesaria),Snackbar.LENGTH_SHORT).show()
                }

                val selectedRadioButton = findViewById<RadioButton>(GeneroRadioGroup.checkedRadioButtonId)

                usuario.genero = selectedRadioButton.text.toString()

                if(usaPasswordCheckbox.isChecked){
                    if(PasswordEditText.text.isEmpty() || RecoveryET.text.isEmpty()){
                        Snackbar.make(it,getString(R.string.contrasena_necesaria),Snackbar.LENGTH_LONG).show()
                    }else{
                        usuario.password = PasswordEditText.text.toString()
                        usuario.email_recuperacion = RecoveryET.text.toString()
                        saveUserToDB(usuario)
                    }
                }else{
                    saveUserToDB(usuario)
                }
            }

        }

        iconoUsuarioCV.setOnClickListener {
            val builder = AlertDialog.Builder(this@RegistrarUsuarioActivity)
            builder.setTitle("¿Eliminar fotografía?")
                    .setPositiveButton("Eliminar"){ dialog, which ->
                        Toast.makeText(this@RegistrarUsuarioActivity,"Estas eliminando la foto", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancelar"){ dialog, which ->

                    }
            val dialog  = builder.create()
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_photo,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_photo_item ->{

                val builder = AlertDialog.Builder(this@RegistrarUsuarioActivity)
                builder.setTitle(getString(R.string.elegir_imagen_de_perfil))
                        .setItems(R.array.origen_imagen) { dialog, which ->
                            when(which){
                                0 -> {
                                    targetW = iconoUsuarioIV.width
                                    targetH = iconoUsuarioIV.height
                                    pickFromGallery()
                                }
                                1 -> {
                                    targetW = iconoUsuarioIV.width
                                    targetH = iconoUsuarioIV.height
                                    if(!mCurrentPhotoPath.isEmpty()){
                                        deleteImageFile()
                                        iconoUsuarioIV.setImageResource(R.drawable.ic_user)
                                    }
                                    dispatchTakePicktureIntent()
                                }
                            }
                        }
                val dialog = builder.create()
                dialog.show()
                return true
            }
            android.R.id.home ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveUserToDB(usuario: Usuario){
        if(intent.hasExtra("USER_ID")){
            usuarioViewModel.update(usuario)

        }else{
            usuarioViewModel.insert(usuario)
        }
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ANADIR_FOTOGRAFIA && resultCode == Activity.RESULT_OK){
            setPic()
        }
        else if(requestCode == CodigosDeSolicitud.SELECCIONAR_IMAGEN && resultCode == Activity.RESULT_OK){
            val selectedImageUri = data?.data

            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = this.contentResolver.query(selectedImageUri, filePathColum,null, null, null)
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColum[0])
            val imgDecodableString = cursor.getString(columnIndex)
            cursor.close()


            val bmOptions = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
                BitmapFactory.decodeFile(imgDecodableString,this)
                val photoW: Int = outWidth
                val photoH: Int = outHeight

                val scaleFactor : Int = Math.min(photoW / targetW , photoH / targetH )

                inJustDecodeBounds = false
                inSampleSize = scaleFactor
                inPurgeable = true
            }

            val imageFromGallery = BitmapFactory.decodeFile(imgDecodableString, bmOptions)
            iconoUsuarioIV.setImageBitmap(imageFromGallery)

        }
    }

    @Throws(IOException::class)
    private fun createImageFile() : File{

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_",
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

    @Throws(IOException::class)
    private fun rescaleImage(scaledBitmap : Bitmap){
        val photoFile = File(mCurrentPhotoPath)

        val out = FileOutputStream(photoFile)
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG,85,out)
        out.close()
    }

    private fun dispatchTakePicktureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile : File? = try{
                    createImageFile()
                } catch (ex : IOException){
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.kps.spart.android.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CodigosDeSolicitud.ANADIR_FOTOGRAFIA)
                }
            }

        }
    }

    private fun setPic(){

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(mCurrentPhotoPath,this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor : Int = Math.min(photoW / targetW , photoH / targetH )

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }

        val exif = ExifInterface(mCurrentPhotoPath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED)


        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)?.also { bitmap ->
        rescaleImage(bitmap)
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
            //rescaleImage()
            iconoUsuarioIV.setImageBitmap(rotatedBitmap)
        }
    }

    fun rotateImage(source : Bitmap, angle : Float) : Bitmap{
        val matrix = Matrix()
        matrix.postRotate(angle)

        return Bitmap.createBitmap(source,0,0,source.width,source.height,matrix,true)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.run {
            putString("ActualPhotoPath", mCurrentPhotoPath)
            putInt("ActualWidth", targetW)
            putInt("ActualHeight", targetH)

        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.run {
            mCurrentPhotoPath = getString("ActualPhotoPath")
            if(!mCurrentPhotoPath.isEmpty()){
                targetW = getInt("ActualWidth")
                targetH = getInt("ActualHeight")
                setPic()
            }
        }
    }

    fun pickFromGallery(){
        Intent(Intent.ACTION_PICK).also { selectPictureIntent ->
            selectPictureIntent.setType("image/*")
            val mimeTypes = arrayOf("image/jpg", "image/png")
            selectPictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(selectPictureIntent,CodigosDeSolicitud.SELECCIONAR_IMAGEN)

        }
    }

}
