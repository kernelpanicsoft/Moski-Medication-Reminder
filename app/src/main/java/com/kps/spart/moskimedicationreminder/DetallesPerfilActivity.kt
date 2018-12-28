package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.database.DatabaseUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AlertDialog
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
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesPerfilActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    dialog, which ->
                    when(which){
                        0-> {
                            val nav = Intent(this@DetallesPerfilActivity, RegistrarUsuarioActivity::class.java)
                            nav.putExtra("USER_ID", user_id)
                            startActivityForResult(nav,349)
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


    private fun populateUserFieldsFromDB(){
        dbHelper = mmrbd(this@DetallesPerfilActivity)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(MMDContract.columnas.NOMBRE_USUARIO,MMDContract.columnas.APELLIDOS_USUARIO, MMDContract.columnas.GENERO_USUARIO, MMDContract.columnas.EDAD_USUARIO)

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$user_id")
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

    private fun deleteUser(){
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$user_id")
        val deletedRows = db.delete(MMDContract.columnas.TABLA_USUARIO, selection, selectionArgs)

        if(deletedRows == 1){
            Toast.makeText(this@DetallesPerfilActivity,getString(R.string.usuario_eliminado_correctamente),Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this@DetallesPerfilActivity, getString(R.string.no_es_posible_eliminar_al_usuario),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        dbHelper.close()
    }
}
