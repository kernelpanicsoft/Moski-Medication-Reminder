package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.support.v7.widget.SearchView
import android.widget.Toast
import elements.Medicamento
import kotlinx.android.synthetic.main.fragment_medicamentos.*


class MedicamentosFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var medicamentoViewModel : MedicamentoViewModel
    lateinit var RV : RecyclerView
    var medicamentos : List<Medicamento>? = null
    lateinit var adapter: MedicamentosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_medicamentos, container, false)

        RV = v.findViewById<View>(R.id.RecViewMedicamentos) as RecyclerView
        RV.setHasFixedSize(true)

        return v
    }

    override fun onResume() {
        super.onResume()

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        mLayoutManager.stackFromEnd = true
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)


        val adapter = MedicamentosAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)

        medicamentoViewModel = ViewModelProviders.of(this).get(MedicamentoViewModel::class.java)
        medicamentoViewModel.getMedicamentosUsuario(usuarioID).observe(this, Observer<List<Medicamento>>{
            adapter.submitList(it)
            medicamentos = it
        })


        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesMedicamentoActivity::class.java)
            val medicamentoSeleccionado = adapter.getMedicamentoAt(RV.getChildAdapterPosition(it))
            nav.putExtra("MEDICINE_ID", medicamentoSeleccionado.id)

            startActivity(nav)
        })

        RV.adapter = adapter
    }




    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)

        val menuItem = menu?.findItem(R.id.itemSearch)
        val searchView : SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemSearch -> { }
            R.id.itemADD -> {
                val nav = Intent(context, AnadirMedicamentoActivity::class.java)
                //medicamentoViewModel.insert(Medicamento(0,"Propanolol","Inderalici","500mg","HOla","Capsula",2345,"NA",1))
                ////Toast.makeText(context, "Tamaño de lista: " +  RecViewMedicamentos.adapter.itemCount, Toast.LENGTH_SHORT).show()
                startActivity(nav)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.d("Medicamento", newText)
        val userInput = newText?.toLowerCase()
        val newList = arrayListOf<Medicamento>()


        for(medicamento: Medicamento in medicamentos!!){
            if(medicamento.nombreMedicamento?.toLowerCase()?.contains(userInput)!!){
                newList.add(medicamento)
            }
        }

        adapter.updateList(newList)
        return true
    }
}// Required empty public constructor
