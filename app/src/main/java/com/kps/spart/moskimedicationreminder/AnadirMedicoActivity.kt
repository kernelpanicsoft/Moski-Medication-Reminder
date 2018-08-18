package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_anadir_medico.*



class AnadirMedicoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medico)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.anadirMedico)


        spinnerTitulo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TituloMedico))

        spinnerTitulo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@AnadirMedicoActivity,"# en adaptador: "+ position , Toast.LENGTH_SHORT).show()

            }
        }

        SpinnerEspecialidad.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.especialidades))

        val fichas = ArrayList<FichaContacto>();
        //val fichaContactoDefault = FichaContacto("Consultorio personal","Torre medica 3 Hospital Upaep","23234343","53453","rwerwe@gf.com","google.com",true)
        val fichaContactoDefault = FichaContacto()
        fichaContactoDefault.accesoRapido = true
        fichas.add(fichaContactoDefault)
        val adapter = FichaDeContactoVaciaAdapter(fichas)

        RecViewfichasContacto.setHasFixedSize(true)
        RecViewfichasContacto.layoutManager = LinearLayoutManager(this@AnadirMedicoActivity, LinearLayoutManager.VERTICAL, false)
        RecViewfichasContacto.adapter = adapter
        RecViewfichasContacto.isNestedScrollingEnabled = false

        //Codigo para añadir una ficha de contacto al medico en cuestion a la entrada del usuario
        /*
        ButtonAnadirFicha.setOnClickListener(
                View.OnClickListener {
                    val lastItem = adapter.getLastAddedItem()
                    if(lastItem.titulo.isBlank() && (lastItem.direccion.isBlank() || lastItem.telefono.isBlank() || lastItem.celular.isBlank() || lastItem.email.isBlank() || lastItem.sitioweb.isBlank()) ){
                        Toast.makeText(this,"No es posible añadir una ficha de contacto teniendo una ficha de contacto sin titulo y algún otro dato de contacto", Toast.LENGTH_LONG).show()
                    }else{
                        val fichaNueva  = FichaContacto()
                       // RecViewfichasContacto.setItemViewCacheSize(adapter.itemCount + 1)
                        adapter.addItem(fichaNueva)

                    }
                }
        )
        */


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSave -> {
                Toast.makeText(this, "Estás guardado la farmacia", Toast.LENGTH_SHORT).show()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }


    fun nuevaFicha(v : View){
        Toast.makeText(this, "Estas precionando la nueva ficha", Toast.LENGTH_SHORT).show()
    }
}
