package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TratamientoViewModel
import alarms.TomasSchedulerService
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import elements.JoinMedicamentoTratamientoData

import model.CodigosDeSolicitud


class TratamientosFragment : Fragment(), SearchView.OnQueryTextListener {


    lateinit var tratamientoViewModel : TratamientoViewModel
    lateinit var RV : RecyclerView
    var tratamientos : List<JoinMedicamentoTratamientoData>? = null
    lateinit var adapter: TratamientosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_tratamientos, container, false)
        RV = v.findViewById<RecyclerView>(R.id.RecViewTratamientos)
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


        adapter = TratamientosAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)
        tratamientoViewModel = ViewModelProviders.of(this).get(TratamientoViewModel::class.java)
        tratamientoViewModel.getTratamientosUsuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
            tratamientos = it

        })
        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesTratamientoActivity::class.java)
            val tratamientoSeleccionado = adapter.getTratamientoAt(RV.getChildAdapterPosition(it))
            nav.putExtra("TRATAMIENTO_ID", tratamientoSeleccionado.tratamientoID)
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
            R.id.itemADD -> {
                val nav = Intent(context, AnadirTratamientoActivity::class.java)
                startActivityForResult(nav,CodigosDeSolicitud.ANADIR_TRATAMIENTO)
                return true
            }
            R.id.itemSearch ->{
                Log.d("REDUCIR","Estas reduciendo los dias de tratamientos")
                tratamientoViewModel.DecreaseTratramientoUnDia()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ANADIR_TRATAMIENTO){
            if(resultCode == Activity.RESULT_OK){
                tratamientoViewModel.getLastID().observe(this, Observer {
                    val addShots = Intent(context,AnadirTomasActivity::class.java)
                    addShots.putExtra("TREATMENT_ID", it)
                    startActivityForResult(addShots, CodigosDeSolicitud.ANADIR_TOMAS)

                })
            }
        }
        if(requestCode == CodigosDeSolicitud.ANADIR_TOMAS){
            if(resultCode == Activity.RESULT_OK){
                val setupShoots = Intent(context,TomasSchedulerService::class.java)
                context?.startService(setupShoots)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText?.toLowerCase()
        val newList = arrayListOf<JoinMedicamentoTratamientoData>()


        for(tratamiento: JoinMedicamentoTratamientoData in tratamientos!!){
            if(tratamiento.titulo?.toLowerCase()?.contains(userInput)!!){
                newList.add(tratamiento)
            }
        }

        adapter.updateList(newList)
        return true    }

}
