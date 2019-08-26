package com.kps.spart.moskimedicationreminder

import MMR.viewModels.EstablecimientoViewModel
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
import android.view.*
import android.widget.LinearLayout
import elements.Establecimiento
import model.CodigosDeSolicitud


class EstablecimientosFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var establecimientoViewModel : EstablecimientoViewModel
    lateinit var RV: RecyclerView
    var establecimientos : List<Establecimiento>? = null
    lateinit var adapter: EstablecimientoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_farmacias, container, false)
        RV = v.findViewById<View>(R.id.RecViewFarmacias) as RecyclerView
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



        adapter = EstablecimientoAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)
        establecimientoViewModel = ViewModelProviders.of(this).get(EstablecimientoViewModel::class.java)
        establecimientoViewModel.getEstablecimientosUsuario(usuarioID).observe(this, Observer<List<Establecimiento>>{
            adapter.submitList(it)
            establecimientos = it
        })

        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesEstablecimientoActivity::class.java)
            val establecimientoSeleccionado = adapter.getEstablecimientoAt(RV.getChildAdapterPosition(it))
            nav.putExtra("ESTABLISHMENT_ID", establecimientoSeleccionado.id)

            startActivity(nav)
        })

        RV.adapter = adapter

        //  establecimientoViewModel.insert(Establecimiento(0,"Prueba","Farmacia","NA", "232423232", "23232323", "we@fd.com","www.com.cm",2.9,23.0, 1))
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
                val nav = Intent(context, AnadirEstablecimientoActivity::class.java)
                startActivity(nav)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText?.toLowerCase()
        val newList = arrayListOf<Establecimiento>()


        for(establecimiento: Establecimiento in establecimientos!!){
            if(establecimiento.nombre?.toLowerCase()?.contains(userInput)!!){
                newList.add(establecimiento)
            }
        }

        adapter.updateList(newList)
        return true    }
}// Required empty public constructor
