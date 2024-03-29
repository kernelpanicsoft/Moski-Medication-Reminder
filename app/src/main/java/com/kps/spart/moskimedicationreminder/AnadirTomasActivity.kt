package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TomaViewModel
import android.app.Activity
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import elements.Toma
import kotlinx.android.synthetic.main.activity_anadir_tomas.*
import model.EstatusToma
import model.MMDContract
import java.text.SimpleDateFormat
import java.util.*

class AnadirTomasActivity : AppCompatActivity() {
    val calendario = Calendar.getInstance()
    val sdf = SimpleDateFormat("h:mm a")
    lateinit var tomasViewModel : TomaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_tomas)

        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.registrar_toma)

        val idTratamiento = intent.getLongExtra("TREATMENT_ID", -1)
        Log.d("IDTRATAMIENTO:", idTratamiento.toString())
        RecViewAddTomas.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RecViewAddTomas.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RecViewAddTomas.context, LinearLayout.VERTICAL)
        RecViewAddTomas.addItemDecoration(dividerItemDecoration)


        tomasViewModel = ViewModelProviders.of(this).get(TomaViewModel::class.java)
        val adapter = RegistrarTomasAdapter(tomasViewModel)
        tomasViewModel.getTomasTratamiento(idTratamiento.toInt()).observe(this, Observer{
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this@AnadirTomasActivity)
            builder.setTitle(getString(R.string.eliminar_toma))
                    .setPositiveButton(getString(R.string.eliminar)){
                        dialog, id ->
                        tomasViewModel.delete(adapter.getShootAt(RecViewAddTomas.getChildAdapterPosition(it)))

                    }
                    .setNegativeButton(getString(R.string.cancelar)){
                        dialog, id ->
                    }

            val dialog = builder.create()
            dialog.show()
        })

        RecViewAddTomas.adapter = adapter



        addShootFAB.setOnClickListener {
            val timePickerFragment = TimePickerDialog(this@AnadirTomasActivity, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                calendario.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendario.set(Calendar.MINUTE, minute)

               // Toast.makeText(this@AnadirTomasActivity,"Hora: " + sdf.format(calendario.time) + "ID Del tratamiento: " + idTratamiento,Toast.LENGTH_SHORT).show()
                val nuevaToma = Toma(0,EstatusToma.PROGRAMADA,sdf.format(calendario.time),idTratamiento.toInt())
                tomasViewModel.insert(nuevaToma)
            }, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this@AnadirTomasActivity))

            timePickerFragment.show()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            R.id.itemSave -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
