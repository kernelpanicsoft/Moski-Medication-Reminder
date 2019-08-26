package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
import alarms.CitasSchedulerService
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
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import elements.CitaMedica
import model.CodigosDeSolicitud

import model.MMDContract


class CitasMedicasFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var citaMedicasViewModel : CitaMedicaViewModel
    private var cita_id : Int = -1
    var citasMedicas : List<CitaMedica>? = null
    lateinit var adapter: CitasAdapter


    lateinit var RV : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_citas_medicas, container, false)
        RV = v.findViewById<View>(R.id.RecViewCitas) as RecyclerView
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

        adapter = CitasAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)
        citaMedicasViewModel = ViewModelProviders.of(this).get(CitaMedicaViewModel::class.java)
        citaMedicasViewModel.getCitasUsuario(usuarioID).observe(this, Observer{
            adapter.submitList(it)
            citasMedicas = it
        })

        adapter.setOnItemClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesCitaMedicaActivity::class.java)
            val citaSelecciona = adapter.getCitaMedicaAt(RV.getChildAdapterPosition(it))
            nav.putExtra("CITA_ID", citaSelecciona.id)
            startActivity(nav)

        })

        RV.adapter = adapter

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)

        val menuItem = menu?.findItem(R.id.itemSearch)
        val searchView : android.support.v7.widget.SearchView = menuItem?.actionView as android.support.v7.widget.SearchView
        searchView.setOnQueryTextListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemSearch -> {}
            R.id.itemADD -> {
                val nav = Intent(context, AnadirCitaMedicaActivity::class.java)
                startActivityForResult(nav, CodigosDeSolicitud.ANADIR_CITA_MEDICA)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ANADIR_CITA_MEDICA){
            if(resultCode == Activity.RESULT_OK){
                val setUpAppointments = Intent(context, CitasSchedulerService::class.java)
                context?.startService(setUpAppointments)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText?.toLowerCase()
        val newList = arrayListOf<CitaMedica>()


        for(citaMedica: CitaMedica in citasMedicas!!){
            if(citaMedica.titulo?.toLowerCase()?.contains(userInput)!!){
                newList.add(citaMedica)
            }
        }

        adapter.updateList(newList)
        return true    }
}// Required empty public constructor
