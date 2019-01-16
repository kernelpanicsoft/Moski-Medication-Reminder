package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import elements.Medicamento
import kotlinx.android.synthetic.main.fragment_medicamentos.*


class MedicamentosFragment : Fragment() {

    lateinit var medicamentoViewModel : MedicamentoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_medicamentos, container, false)

        val RV = v.findViewById<View>(R.id.RecViewMedicamentos) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)


        val adapter = MedicamentosAdapter(context)
        medicamentoViewModel = ViewModelProviders.of(this).get(MedicamentoViewModel::class.java)
        medicamentoViewModel.allMedicamentos.observe(this, Observer<List<Medicamento>>{
            adapter.submitList(it)
        })


        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesMedicamentoActivity::class.java)
            val medicamentoSeleccionado = adapter.getMedicamentoAt(RV.getChildAdapterPosition(it))
            nav.putExtra("MEDICINE_ID", medicamentoSeleccionado.id)

            startActivity(nav)
        })

        RV.adapter = adapter


        return v
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_add, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                //val nav = Intent(context, AnadirMedicamentoActivity::class.java)
                medicamentoViewModel.insert(Medicamento(0,"Propanolol","Inderalici","500mg","HOla","Capsula",2345,"NA",1))
                Toast.makeText(context, "Tamaño de lista: " +  RecViewMedicamentos.adapter.itemCount, Toast.LENGTH_SHORT).show()
                //startActivity(nav)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}// Required empty public constructor
