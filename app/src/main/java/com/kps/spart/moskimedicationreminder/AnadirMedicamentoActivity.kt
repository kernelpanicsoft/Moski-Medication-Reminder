package com.kps.spart.moskimedicationreminder

import android.graphics.Color
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_anadir_medicamento.*
import org.xdty.preference.colorpicker.ColorPickerDialog
import org.xdty.preference.colorpicker.ColorPickerSwatch
import java.util.Random


class AnadirMedicamentoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_medicamento)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.AnadirMedicamento)

        SpinnerTipoMedicamento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.resources.getStringArray(R.array.TipoMedicamento))

        var selectedColor = ContextCompat.getColor(this@AnadirMedicamentoActivity,R.color.blueberry)


        ColorMedicamentoButton.setOnClickListener(
                View.OnClickListener {




                    val mColors : IntArray =  resources.getIntArray(R.array.default_rainbow)

                    var dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                            mColors,
                            selectedColor,
                            5,
                            ColorPickerDialog.SIZE_SMALL,
                            true,
                            0,
                            Color.BLACK
                    )
                    dialog.setOnColorSelectedListener(ColorPickerSwatch.OnColorSelectedListener {

                        color ->
                        selectedColor = color

                        iconoMedicamento.setColorFilter(selectedColor)

                    })


                    dialog.show(fragmentManager,"color_dialog_test")
                }
        )




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


}
