package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import Elementos.Medico
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_anadir_medico.*
import model.MMDContract
import model.mmrbd


class AnadirMedicoActivity : AppCompatActivity() {

    private lateinit var dbHelper : mmrbd
    private lateinit var medico : Medico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medico)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.anadirMedico)

        dbHelper = mmrbd(this@AnadirMedicoActivity)



        spinnerTitulo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TituloMedico))
        spinnerTitulo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                medico.titulo = parent?.getItemAtPosition(position).toString()

            }
        }

        SpinnerEspecialidad.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.especialidades))
        SpinnerEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                medico.especialidad = parent?.getItemAtPosition(position).toString()
            }
        }

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
                medico.nombre = textInputLayoutNombre.text.toString()

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

    private fun saveMedicToBD(){
        val db = dbHelper.writableDatabase
        val errorATInsertion : Long = -1

        val newRowId = db.insert(MMDContract.columnas.TABLA_DOCTOR, null, medico.toContentValues())

        if(newRowId == errorATInsertion){
            Toast.makeText(this@AnadirMedicoActivity,getString(R.string.ocurrio_un_problema_nuevo_doctor),Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@AnadirMedicoActivity, getString(R.string.doctor_registrado_correctamente),Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
