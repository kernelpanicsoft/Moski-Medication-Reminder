package com.kps.spart.moskimedicationreminder

import elements.FichaContacto
import elements.Medico
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_anadir_medico.*
import model.MMDContract
import model.mmrbd
import org.xdty.preference.colorpicker.ColorPickerDialog


class AnadirMedicoActivity : AppCompatActivity() {

    private lateinit var dbHelper : mmrbd
    private lateinit var medico : Medico
    private lateinit var adapter : FichaDeContactoCompactaAdapter
    private var fichas = ArrayList<FichaContacto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medico)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.anadirMedico)

        dbHelper = mmrbd(this@AnadirMedicoActivity)
        medico = Medico()


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


        adapter = FichaDeContactoCompactaAdapter(this@AnadirMedicoActivity, fichas)

        RecViewfichasContacto.setHasFixedSize(true)
        val mLAyoutManager = LinearLayoutManager(this@AnadirMedicoActivity,LinearLayoutManager.VERTICAL, false)

        RecViewfichasContacto.layoutManager = mLAyoutManager
        RecViewfichasContacto.adapter = adapter

        var selectedColor = ContextCompat.getColor(this@AnadirMedicoActivity,R.color.blueberry)
        medico.colorIcono = selectedColor.toString()

        val colors = resources.getIntArray(R.array.default_rainbow)
        iconoMedicoIV.setOnClickListener{

            val colorPickerDialog = ColorPickerDialog.newInstance(R.string.colorDistintivo,
                    colors,
                    selectedColor,
                    5,
                    ColorPickerDialog.SIZE_SMALL,
                    true
            )

            colorPickerDialog.setOnColorSelectedListener { color ->
                selectedColor = color
                iconoMedicoIV.setColorFilter(selectedColor)
                medico.colorIcono = selectedColor.toString()
            }

            colorPickerDialog.show(fragmentManager,"color_picker_dialer")


        }

        addFichaContactoButton.setOnClickListener{
            val builder = AlertDialog.Builder(this@AnadirMedicoActivity)
            val inflatedView = layoutInflater.inflate(R.layout.list_item_ficha_contacto, null)
            builder.setTitle(getString(R.string.anadir_ficha_de_contacto))
                    .setView(inflatedView)
                    .setPositiveButton(getString(R.string.guardar), null)
                    .setNegativeButton(getString(R.string.cancelar)) { dialog, which ->

                    }

            val dialog = builder.create()
            dialog.setOnShowListener{
                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener{
                    val fichaContacto = FichaContacto()
                    val tituloFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutTituloficha)
                    val direccionFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutDireccion)
                    val telefonoFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutTelefono)
                    val celularFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutTelefono)
                    val emailFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutEmail)
                    val sitioWebFichaContactoET = inflatedView.findViewById<EditText>(R.id.textInputLayoutWebSite)

                    fichaContacto.titulo = tituloFichaContactoET.text.toString()
                    fichaContacto.direccion = direccionFichaContactoET.text.toString()
                    fichaContacto.telefono = telefonoFichaContactoET.text.toString()
                    fichaContacto.celular = celularFichaContactoET.text.toString()
                    fichaContacto.email = emailFichaContactoET.text.toString()
                    fichaContacto.sitioweb = sitioWebFichaContactoET.text.toString()


                    if(fichaContacto.titulo.isNullOrEmpty() && (fichaContacto.direccion.isNullOrEmpty() || fichaContacto.telefono.isNullOrEmpty() || fichaContacto.celular.isNullOrEmpty() || fichaContacto.email.isNullOrEmpty() || fichaContacto.sitioweb.isNullOrEmpty())){
                        Toast.makeText(this@AnadirMedicoActivity,getString(R.string.es_necesario_especificar_el_titulo_y_otro_campo),Toast.LENGTH_LONG).show()
                    }else{
                        fichas.add(fichaContacto)
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }


            }


            }
            dialog.show()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSave -> {
                medico.nombre = textInputLayoutNombre.text.toString()

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirMedicoActivity)
                val usuarioID = sharedPref.getInt("actualUserID", -1)

                if(usuarioID != -1){
                    medico.usuarioID = usuarioID
                    saveMedicToBD()

                }
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun saveMedicToBD(){
        val db = dbHelper.writableDatabase
        val errorAtInsertion : Long = -1

        val newRowId = db.insert(MMDContract.columnas.TABLA_DOCTOR, null, medico.toContentValues())

        if(newRowId == errorAtInsertion){
            Toast.makeText(this@AnadirMedicoActivity,getString(R.string.ocurrio_un_problema_nuevo_doctor),Toast.LENGTH_SHORT).show()
        }else{
            if(adapter.itemCount > 0){
                saveContactCardsToBD(adapter)
            }
            Toast.makeText(this@AnadirMedicoActivity, getString(R.string.doctor_registrado_correctamente),Toast.LENGTH_SHORT).show()
        }


    }

    private fun saveContactCardsToBD(adapter : FichaDeContactoCompactaAdapter){
        val db = dbHelper.writableDatabase
        val errorAtInsertion : Long = -1


        if(adapter.itemCount > 0){
            for(contactCard in fichas){

                val newRowId = db.insert(MMDContract.columnas.TABLA_FICHA_CONTACTO, null, contactCard.toContentValues())
                if(newRowId == errorAtInsertion){
                    break
                }
            }
            finish()
        }else{
            Toast.makeText(this@AnadirMedicoActivity,"Por favor rellene al menos una ficha de contacto", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelableArrayList("contactCards",fichas)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        fichas = savedInstanceState!!.getParcelableArrayList("contactCards")
        adapter = FichaDeContactoCompactaAdapter(this@AnadirMedicoActivity, fichas)

        RecViewfichasContacto.setHasFixedSize(true)
        val mLAyoutManager = LinearLayoutManager(this@AnadirMedicoActivity,LinearLayoutManager.VERTICAL, false)

        RecViewfichasContacto.layoutManager = mLAyoutManager

        RecViewfichasContacto.adapter = adapter

    }
}
