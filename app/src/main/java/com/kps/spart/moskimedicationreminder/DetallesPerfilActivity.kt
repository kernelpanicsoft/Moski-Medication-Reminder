package com.kps.spart.moskimedicationreminder

import android.database.DatabaseUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalles_perfil.*
import model.MMDContract
import model.mmrbd

class DetallesPerfilActivity : AppCompatActivity() {

    private var user_id : Int = -1
    private lateinit var dbHelper : mmrbd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_perfil)

        toolbar.title = getString(R.string.detalle_usuario)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        user_id = intent.getIntExtra("USER_ID", -1)

     //   Toast.makeText(this@DetallesPerfilActivity,"Valor: " + user_id, Toast.LENGTH_SHORT).show()

        populateUserFieldsFromDB()

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
        }
        return super.onOptionsItemSelected(item)
    }


    private fun populateUserFieldsFromDB(){
        dbHelper = mmrbd(this@DetallesPerfilActivity)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(MMDContract.columnas.NOMBRE_USUARIO,MMDContract.columnas.APELLIDOS_USUARIO, MMDContract.columnas.GENERO_USUARIO, MMDContract.columnas.EDAD_USUARIO)

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${user_id}")
        val cursor = db.query(
                MMDContract.columnas.TABLA_USUARIO,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
                )


      //  Log.v("Valores del cursor: ", DatabaseUtils.dumpCursorToString(cursor))


        if(cursor.moveToFirst()){
            NombreApellidosUsuarioTV.text = "${cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.NOMBRE_USUARIO))} ${cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.APELLIDOS_USUARIO))}"
            GeneroUsuarioTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.GENERO_USUARIO))
            EdadUsuarioTV.text = cursor.getString(cursor.getColumnIndexOrThrow(MMDContract.columnas.EDAD_USUARIO))
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        dbHelper.close()
    }
}
