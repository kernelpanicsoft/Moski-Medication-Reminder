package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicoViewModel
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
import android.widget.Toast
import elements.Medico
import model.CodigosDeSolicitud


class MedicosFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var medicoViewModel : MedicoViewModel
    lateinit var RV: RecyclerView
    var medicos : List<Medico>? = null
    lateinit var adapter : MedicosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_medicos, container, false)

        RV = v.findViewById<RecyclerView>(R.id.RecViewMedicos)
        RV.setHasFixedSize(true)
        return v
    }

    override fun onResume(){
        super.onResume()

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        mLayoutManager.stackFromEnd = true
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        adapter = MedicosAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID",-1)
        medicoViewModel = ViewModelProviders.of(this).get(MedicoViewModel::class.java)
        medicoViewModel.getMedicosUsuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
            medicos = it
        })
        adapter.setOnItemClickListener(View.OnClickListener {
            val nav = Intent(context,DetallesMedicoActivity::class.java)
            val medicoSeleccionado = adapter.getMedicAt(RV.getChildAdapterPosition(it))
            nav.putExtra("MEDIC_ID", medicoSeleccionado.id)
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
        when(item!!.itemId){
            R.id.itemSearch -> { }
            R.id.itemADD -> {
                val nav: Intent = Intent(context, AnadirMedicoActivity::class.java)
                startActivityForResult(nav, CodigosDeSolicitud.ANADIR_MEDICO)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText?.toLowerCase()
        val newList = arrayListOf<Medico>()


        for(medico: Medico in medicos!!){
            if(medico.nombre?.toLowerCase()?.contains(userInput)!!){
                newList.add(medico)
            }
        }

        adapter.updateList(newList)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ANADIR_MEDICO){
            if(resultCode == Activity.RESULT_OK){
                val medicosViewModel : MedicoViewModel = ViewModelProviders.of(this).get(MedicoViewModel::class.java)
                medicosViewModel.getLastInsertedID().observe(this, Observer {
                    val nav = Intent(context,AnadirFichaContactoActivity::class.java)
                    nav.putExtra("MEDIC_ID", it?.toInt())
                    startActivity(nav)
                })

            }
        }
    }

}// Required empty public constructor
