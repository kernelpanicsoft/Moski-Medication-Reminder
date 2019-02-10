package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.BaseColumns
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import elements.Usuario
import kotlinx.android.synthetic.main.activity_listar_usuarios.*
import model.CodigosDeSolicitud


class ListarUsuariosActivity : AppCompatActivity() {

    lateinit  var usuarioViewModel : UsuarioViewModel



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
            0
            //usuarioViewModel.delete(usuarioSeleccionado)

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@ListarUsuariosActivity)
            with(sharedPref.edit()){
                putInt("actualUserID",usuarioSeleccionado.uid)
                commit()
            }

            finish()
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
        //    var usuario = Usuario(0,"Sopita","De caracol",23,"Masculino","dfdf","DFDF","DFDGF")
        //    usuarioViewModel.insert(usuario)
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
            Toast.makeText(this,  getString(R.string.usuario_creado_correctamente), Toast.LENGTH_SHORT).show()
        }else if (requestCode == CodigosDeSolicitud.ELIMINAR_USUARIO && resultCode == Activity.RESULT_OK){

            val usuarioAEliminar : Usuario = usuarioViewModel.getUsuario(data!!.getIntExtra("USER_ID", -1)) as Usuario
          //  usuarioViewModel.delete(usuarioAEliminar)
            Toast.makeText(this,  getString(R.string.usuario_eliminado_correctamente), Toast.LENGTH_SHORT).show()
        }
    }

}
