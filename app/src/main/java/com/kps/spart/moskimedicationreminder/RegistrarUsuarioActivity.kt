package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import elements.Usuario
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registrar_usuario.*
import model.CodigosDeSolicitud
import model.MMDContract
import model.mmrbd

class RegistrarUsuarioActivity : AppCompatActivity() {

    lateinit var usuarioViewModel : UsuarioViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)


        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        usuarioViewModel = ViewModelProviders.of(this@RegistrarUsuarioActivity).get(UsuarioViewModel::class.java)

        if(intent.hasExtra("USER_ID")){
            title = getString(R.string.editar_usuario)
            val usuarioLive = usuarioViewModel.getUsuario(intent.getIntExtra("USER_ID", -1))


            val usuarioAEditar = usuarioLive.value
            Toast.makeText(this@RegistrarUsuarioActivity,"Datos: " + usuarioAEditar?.nombre, Toast.LENGTH_SHORT).show()
            nombreUsuarioET.setText(usuarioAEditar?.nombre)

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
            val usuario = Usuario(0)

            if(nombreUsuarioET.text.isEmpty() || apellidoUsuarioET.text.isEmpty() || Edad.text.isEmpty()){
                Snackbar.make(it,getString(R.string.nombre_apellido_necesario),Snackbar.LENGTH_LONG).show()
            }else{
                usuario.nombre = nombreUsuarioET.text.toString()
                usuario.apellidos = apellidoUsuarioET.text.toString()

                try{
                    usuario.edad = Edad.text.toString().toInt()
                }catch( e : NumberFormatException){

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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_photo,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_photo_item ->{

                val builder = AlertDialog.Builder(this@RegistrarUsuarioActivity)
                builder.setTitle("Elegir imagen de perfil desde:")
                        .setItems(R.array.origen_imagen) { dialog, which ->
                            when(which){
                                0 -> {}
                                1 -> {
                                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                                        takePictureIntent.resolveActivity(packageManager)?.also {
                                            startActivityForResult(takePictureIntent,1044)
                                        }
                                    }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if(requestCode == 1047 && resultCode == Activity.RESULT_OK){
            val imageBitmap = data.extras.get("data") as Bitmap
            iconoUsuarioIV.setImageBitmap(imageBitmap)
        }
    }
}
