package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.database.DatabaseUtils
import android.database.Observable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import elements.Usuario
import kotlinx.android.synthetic.main.activity_detalles_perfil.*
import model.CodigosDeSolicitud
import model.MMDContract
import model.mmrbd

class DetallesPerfilActivity : AppCompatActivity() {

    private var user_id : Int = -1
    lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var usuarioActualLive : LiveData<Usuario>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_perfil)

        toolbar.title = getString(R.string.detalle_usuario)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        user_id = intent.getIntExtra("USER_ID", -1)

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel::class.java)

        usuarioActualLive =  usuarioViewModel.getUsuario(user_id)
        usuarioActualLive.observe(this, Observer {
            populateUserFieldsFromDB(it)

        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesPerfilActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    dialog, which ->
                    when(which){
                        0-> {
                            val nav = Intent(this@DetallesPerfilActivity, RegistrarUsuarioActivity::class.java)
                            nav.putExtra("USER_ID", user_id)
                            startActivityForResult(nav,CodigosDeSolicitud.EDITAR_USUARIO)
                            //usuarioViewModel.update(usuarioActual)
                        }
                        1 -> {
                            val innerBuilder = AlertDialog.Builder(this@DetallesPerfilActivity)
                            innerBuilder.setTitle(getString(R.string.eliminar_usuario))
                                    .setMessage(getString(R.string.esta_seguro_que_desea_eliminar_usuario))
                                    .setPositiveButton(getString(R.string.si)){
                                        dialog, id ->
                                        deleteUser()
                                    }
                                    .setNegativeButton(getString(R.string.no)){
                                        dialog, id ->

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


    private fun populateUserFieldsFromDB(usuario: Usuario?){

            NombreApellidosUsuarioTV.text = "${usuario?.nombre} ${usuario?.apellidos}"
            GeneroUsuarioTV.text = usuario?.genero
            EdadUsuarioTV.text = usuario?.edad.toString()

    }

    private fun deleteUser(){
        if(usuarioActualLive.hasObservers()){
            usuarioActualLive.removeObservers(this@DetallesPerfilActivity)
            usuarioViewModel.delete(usuarioActualLive.value!!)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CodigosDeSolicitud.EDITAR_USUARIO && resultCode == Activity.RESULT_OK){
            Toast.makeText(this@DetallesPerfilActivity,getString(R.string.usuario_actualizado_correctamente), Toast.LENGTH_SHORT).show()
        }
    }

}
