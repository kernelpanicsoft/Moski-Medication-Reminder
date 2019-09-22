package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import elements.Usuario
import kotlinx.android.synthetic.main.activity_listar_usuarios.*
import model.CodigosDeSolicitud
import java.io.*
import java.lang.Exception


class ListarUsuariosActivity : AppCompatActivity() {

    lateinit  var usuarioViewModel : UsuarioViewModel
    var userPassWordHelper : String? = ""
    var userEmailHelper : String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_usuarios)

        toolbar.title = getString(R.string.usuarios_registrados)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        RecViewUsuarios.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this@ListarUsuariosActivity,LinearLayoutManager.VERTICAL,false)
        RecViewUsuarios.layoutManager = mLayoutManager

        val dividerIterator = DividerItemDecoration(RecViewUsuarios.context, LinearLayout.VERTICAL)
        RecViewUsuarios.addItemDecoration(dividerIterator)

        //Asignamos el adaptador a nuestro Recyclerview
        val adapter = UsuariosAdapter()

        //Especificamos el escucha de eventos para definir el usuario activo de la aplicacion
        adapter.setOnClickListener( View.OnClickListener {
            val usuarioSeleccionado = adapter.getUsuarioAt(RecViewUsuarios.getChildAdapterPosition(it))

            if(!usuarioSeleccionado.password.isNullOrEmpty() && usuarioSeleccionado.uid != getCurrentUserID()){
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.ingresar_contrasena))
                val inflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_input_password, null)
                builder.setView(dialogView)

                val passwordEditText : EditText = dialogView.findViewById(R.id.userPasswordET)
                builder.setPositiveButton(getString(R.string.ingresar)) { dialog, id  ->
                    if(usuarioSeleccionado.password.equals(passwordEditText.text.toString())){
                        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@ListarUsuariosActivity)
                        with(sharedPref.edit()){
                            putInt("actualUserID",usuarioSeleccionado.uid)
                            apply()
                        }
                        finish()
                    }else{
                        Snackbar.make(frameLayoutListaUsuarios,getString(R.string.contrasena_incorrecta), Snackbar.LENGTH_SHORT).show()
                    }
                }
                builder.setNegativeButton(getString(R.string.cancelar)) { dialog, id ->
                }
                builder.setNeutralButton(getString(R.string.recuperar_contrasena)) { dialog, id ->

                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                CodigosDeSolicitud.SOLICITAR_PERMISO_ALMACENAMIENTO_EXTERNO)
                        userPassWordHelper = usuarioSeleccionado.password
                        userEmailHelper = usuarioSeleccionado.email_recuperacion

                    }else{
                        recoverPassWord(usuarioSeleccionado.password, usuarioSeleccionado.email_recuperacion)
                        Log.e("Contrasena", usuarioSeleccionado.password)
                    }

                }
                
                val alertDialog = builder.create()
                alertDialog.show()
            }else{
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@ListarUsuariosActivity)
                with(sharedPref.edit()){
                    putInt("actualUserID",usuarioSeleccionado.uid)
                    apply()
                }
                finish()
            }
        }
        )

        RecViewUsuarios.adapter = adapter

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel::class.java)
        usuarioViewModel.allUsuarios.observe(this, Observer<List<Usuario>>{
            adapter.submitList(it)
        } )


        //Añadimos el evento al Floatting button
        add_user_fab.setOnClickListener {
            val nav = Intent(this@ListarUsuariosActivity,RegistrarUsuarioActivity::class.java)
             startActivityForResult(nav, CodigosDeSolicitud.REGISTRAR_USUARIO)
       
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){

            android.R.id.home ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.REGISTRAR_USUARIO && resultCode == Activity.RESULT_OK){
            usuarioViewModel.getLastUserID().observe(this, Observer {
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
                with(sharedPref.edit()){
                    putInt("actualUserID",it!!.toInt())
                    apply()
                }
                finish()
            })
            Toast.makeText(this,  getString(R.string.usuario_creado_correctamente), Toast.LENGTH_SHORT).show()
        }else if (requestCode == CodigosDeSolicitud.ELIMINAR_USUARIO && resultCode == Activity.RESULT_OK){

            val usuarioAEliminar : Usuario = usuarioViewModel.getUsuario(data!!.getIntExtra("USER_ID", -1)) as Usuario
            usuarioViewModel.delete(usuarioAEliminar)
            Toast.makeText(this,  getString(R.string.usuario_eliminado_correctamente), Toast.LENGTH_SHORT).show()
        }else if (requestCode == CodigosDeSolicitud.RECUPERAR_CONTRASENA && resultCode == Activity.RESULT_CANCELED){
          //  deletePasswordFile()
        }


    }

    fun recoverPassWord(passWordUsuario: String?, emailRecuperacion: String?){
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "message/rfc822"
        sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailRecuperacion))
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.MMR_Recuperar_Contrasena))
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.su_contrasena_archivo_adjunto))

       // val file = generatePassWordFile(this,"MoskiMedicationReminderPassWord.txt", passWordUsuario!!)
        val file = createPasswordFile()
        writePasswordToFile(file, passWordUsuario!!)
        Log.e("Contrasena", userPassWordHelper)

        val fileURI : Uri = FileProvider.getUriForFile(this,
                "com.kps.spart.android.fileprovider",
                file
                )

            sendIntent.putExtra(Intent.EXTRA_STREAM, fileURI)
            sendIntent.setType(".txt -> text/plain")

        startActivityForResult(Intent.createChooser(sendIntent,getString(R.string.enviar_email_recuperacion)), CodigosDeSolicitud.RECUPERAR_CONTRASENA)
    }

    fun getCurrentUserID() : Int{
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        return sharedPref.getInt("actualUserID", -1)

    }


    private fun createPasswordFile() : File{
        val storageDir : File = getExternalFilesDir(Environment.DIRECTORY_DCIM)
        return File.createTempFile(
                "MMRPASSWORD",
                ".txt",
                storageDir
        ).apply {
            userPassWordHelper = absolutePath
        }
    }

    @Throws(IOException::class)
    private fun deletePasswordFile(){
        val passwordFile = File(userPassWordHelper)
        val passwordUri: Uri = FileProvider.getUriForFile(
                this,
                "com.kps.spart.android.fileprovider",
                passwordFile
        )

        this.contentResolver.delete(passwordUri,null,null)
    }


    private fun writePasswordToFile(file: File, password: String) {
        val fileStream = FileOutputStream(file)
        val writter = OutputStreamWriter(fileStream,"UTF-16")
        writter.append(password)
        writter.flush()
        writter.close()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CodigosDeSolicitud.SOLICITAR_PERMISO_ALMACENAMIENTO_EXTERNO ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    recoverPassWord(userPassWordHelper,userEmailHelper)
                }else{
                    Toast.makeText(this, getString(R.string.es_necesario_permitir_permisos_archivos), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



}
