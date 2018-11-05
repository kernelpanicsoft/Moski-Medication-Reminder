package com.kps.spart.moskimedicationreminder

import Elementos.Usuario
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_listar_usuarios.*
import model.MMDContract
import model.mmrbd

class ListarUsuariosActivity : AppCompatActivity() {
    lateinit var dbHelper: mmrbd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_usuarios)

        toolbar.title = getString(R.string.usuarios_registrados)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)


        //Añadimos el evento al Floatting button
        add_user_fab.setOnClickListener {
            val nav = Intent(this@ListarUsuariosActivity,RegistrarUsuarioActivity::class.java)
            startActivity(nav)
        }

        RecViewUsuarios.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this@ListarUsuariosActivity,LinearLayoutManager.VERTICAL,false)
        RecViewUsuarios.layoutManager = mLayoutManager

        val dividerIterator = DividerItemDecoration(RecViewUsuarios.context, LinearLayout.VERTICAL)
        RecViewUsuarios.addItemDecoration(dividerIterator)


        //Consultamos la base de datos usando un DB Helper.
        dbHelper = mmrbd(this@ListarUsuariosActivity)
        val db = dbHelper.readableDatabase

        //Creamos la proyeccion de las columnas que deseamos leear
        val projection = arrayOf(BaseColumns._ID,MMDContract.columnas.NOMBRE_USUARIO,MMDContract.columnas.APELLIDOS_USUARIO,MMDContract.columnas.GENERO_USUARIO,MMDContract.columnas.EDAD_USUARIO)

        val cursor = db.query(
                MMDContract.columnas.TABLA_USUARIO,
                projection,
                null,
                null,
                null,
                null,
                null
        )

        /*
       val itemsIds = mutableListOf<Long>()
        with(cursor){
            while(moveToNext()){
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemsIds.add(itemId)
            }
        }
        */

        //Asignamos el adaptador a nuestro Recyclerview
        val adapter = UsuariosAdapter(cursor)

        //Especificamos el escucha de eventos
        adapter.setOnClickListener( View.OnClickListener {
            val nav = Intent(this@ListarUsuariosActivity, DetallesPerfilActivity::class.java)
            nav.putExtra("USER_ID",adapter.getUserID(RecViewUsuarios.getChildAdapterPosition(it)))
            startActivity(nav)
        }
        )

        RecViewUsuarios.adapter = adapter

        Toast.makeText(this@ListarUsuariosActivity,"Se esta llamando " + object{}.javaClass.enclosingMethod.name, Toast.LENGTH_SHORT).show()
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

    override fun onRestart() {
        super.onRestart()

      //  Toast.makeText(this@ListarUsuariosActivity,"Se esta llamando " + object{}.javaClass.enclosingMethod.name, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
