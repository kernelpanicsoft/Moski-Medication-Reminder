package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_anadir_tratamiento.toolbar
import kotlinx.android.synthetic.main.activity_elegir_medicamento.*
import model.CodigosDeSolicitud


class ElegirMedicamentoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elegir_medicamento)

        title = getString(R.string.elegir_medicamento)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        RecViewElegirMedicamento.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this@ElegirMedicamentoActivity, LinearLayoutManager.VERTICAL, false)
        RecViewElegirMedicamento.layoutManager = mLayoutManager

        val divideIterator = DividerItemDecoration(RecViewElegirMedicamento.context, LinearLayout.VISIBLE)
        RecViewElegirMedicamento.addItemDecoration(divideIterator)

        val adapter = MedicamentosAdapter(this@ElegirMedicamentoActivity)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@ElegirMedicamentoActivity)
        val usuarioID = sharedPref.getInt("actualUserID", -1)

        val medicamentoViewModel = ViewModelProviders.of(this).get(MedicamentoViewModel::class.java)
        medicamentoViewModel.getMedicamentosUsuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
        })

        adapter.setOnClickListener(View.OnClickListener {
            val medicamentoSeleccionado = adapter.getMedicamentoAt(RecViewElegirMedicamento.getChildAdapterPosition(it))
            intent.putExtra("SELECTED_MEDICINE_ID",medicamentoSeleccionado.id)

            setResult(Activity.RESULT_OK, intent)
            finish()
        })


        RecViewElegirMedicamento.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
