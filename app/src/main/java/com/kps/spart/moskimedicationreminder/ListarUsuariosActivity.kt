package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_listar_usuarios.*

class ListarUsuariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_usuarios)

        toolbar.title = getString(R.string.usuarios_registrados)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        add_user_fab.setOnClickListener {
            val nav = Intent(this@ListarUsuariosActivity,RegistrarUsuarioActivity::class.java)
            startActivity(nav)
        }

        RecViewUsuarios.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this@ListarUsuariosActivity,LinearLayoutManager.VERTICAL,false)
        RecViewUsuarios.layoutManager = mLayoutManager

        val dividerIterator = DividerItemDecoration(RecViewUsuarios.context, LinearLayout.VERTICAL)
        RecViewUsuarios.addItemDecoration(dividerIterator)



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
}
