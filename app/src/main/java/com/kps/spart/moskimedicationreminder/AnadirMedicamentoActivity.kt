package com.kps.spart.moskimedicationreminder

import elements.Medicamento
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_anadir_medicamento.*
import model.MMDContract
import model.mmrbd
import org.xdty.preference.colorpicker.ColorPickerDialog


class AnadirMedicamentoActivity : AppCompatActivity() {

    lateinit var dbHelper : mmrbd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medicamento)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.AnadirMedicamento)



        dbHelper = mmrbd(this@AnadirMedicamentoActivity)

        SpinnerTipoMedicamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TipoMedicamento))
        SpinnerTipoMedicamento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                //Toast.makeText(this@AnadirMedicamentoActivity, "Elemento seleccionado: " + position.toString(), Toast.LENGTH_SHORT).show()
                when (position){
                    0 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_roundpill) }
                    1 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_tab)}
                    2 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_capsula)}
                    3 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_syrup)}
                    4 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_drops)}
                    5 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_eyedrops)}
                    6 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_ointment)}
                    7 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_powder)}
                    8 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_gel)}
                    9 -> { MedicamentoIconoTV.setImageResource(R.drawable.ic_inhalator)}
                    10-> { MedicamentoIconoTV.setImageResource(R.drawable.ic_suppository)}
                    11-> { MedicamentoIconoTV.setImageResource(R.drawable.ic_intravenous)}
                    12-> { MedicamentoIconoTV.setImageResource(R.drawable.ic_syringe)}
                }
            }
        }

        var selectedColor = ContextCompat.getColor(this@AnadirMedicamentoActivity,R.color.blueberry)


        val colors = resources.getIntArray(R.array.default_rainbow)



        ColorMedicamentoButton.setOnClickListener{
           val colorPickerDialog = ColorPickerDialog.newInstance(R.string.colorDistintivo,
                   colors,
                   selectedColor,
                   5,
                   ColorPickerDialog.SIZE_SMALL,
                   true
                   )
            
            colorPickerDialog.setOnColorSelectedListener { color ->
                selectedColor = color
                MedicamentoIconoTV.setColorFilter(selectedColor)



             //   Toast.makeText(this@AnadirMedicamentoActivity,"Color seleccionado: " + color + " Valor del recurso: "+ String.format("#%06x",(0xFFFFFF and selectedColor)), Toast.LENGTH_SHORT).show()
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


                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirMedicamentoActivity)
                val usuarioID = sharedPref.getInt("actualUserID", -1)

                if(usuarioID != -1){

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


    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

}
