package com.kps.spart.moskimedicationreminder

import Elementos.FichaContacto
import Elementos.Medico
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
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
    private lateinit var adapter : FichaDeContactoVaciaAdapter

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

        val fichas = ArrayList<FichaContacto>()
        //val fichaContactoDefault = FichaContacto("Consultorio personal","Torre medica 3 Hospital Upaep","23234343","53453","rwerwe@gf.com","google.com",true)
        val fichaContactoDefault = FichaContacto()
        fichaContactoDefault.accesoRapido = true
        fichas.add(fichaContactoDefault)
        adapter = FichaDeContactoVaciaAdapter(this@AnadirMedicoActivity,fichas)

        ListViewfichasContacto.adapter = adapter


        var selectedColor = ContextCompat.getColor(this@AnadirMedicoActivity,R.color.blueberry)
        medico.colorIcono = selectedColor.toString()

        val colors = resources.getIntArray(R.array.default_rainbow)
        colorMedicoButton.setOnClickListener{

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
            Toast.makeText(this@AnadirMedicoActivity, getString(R.string.doctor_registrado_correctamente),Toast.LENGTH_SHORT).show()
        }

    //    saveContactCardsToBD(adapter)
    }

    private fun saveContactCardsToBD(adapter : FichaDeContactoVaciaAdapter){
        val db = dbHelper.writableDatabase
        val errorAtInsertion : Long = -1


        if(adapter.count > 0){
            for(contactCard in adapter.items){
                if(!contactCard.titulo.isNullOrEmpty()){
                    val newRowId = db.insert(MMDContract.columnas.TABLA_FICHA_CONTACTO, null, contactCard.toContentValues())
                }
            }
        }else{
            Toast.makeText(this@AnadirMedicoActivity,"Por favor rellene al menos una ficha de contacto", Toast.LENGTH_SHORT).show()
        }
    }
}
