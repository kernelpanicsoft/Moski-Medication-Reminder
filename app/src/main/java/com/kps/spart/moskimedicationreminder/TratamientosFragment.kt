package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TratamientoViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
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

import elements.Tratamiento


class TratamientosFragment : Fragment() {

    lateinit var tratamientoViewModel : TratamientoViewModel
    lateinit var RV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_tratamientos, container, false)
        RV = v.findViewById<RecyclerView>(R.id.RecViewTratamientos)
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)


        val adapter = TratamientosAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuaarioID = sharedPref.getInt("actualUserID", -1)
        tratamientoViewModel = ViewModelProviders.of(this).get(TratamientoViewModel::class.java)
        tratamientoViewModel.getAllTratamientosUsuario(usuaarioID).observe(this, Observer {
            adapter.submitList(it)
            Toast.makeText(context,"Tamaño de lista: " + it?.size, Toast.LENGTH_SHORT).show()
        })
        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesTratamientoActivity::class.java)
            val tratamientoSeleccionado = adapter.getTratamientoAt(RV.getChildAdapterPosition(it))
            nav.putExtra("TRATAMIENTO_ID", tratamientoSeleccionado.id)
            startActivity(nav)
        })


        RV.adapter = adapter
        return v
    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_add, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                val nav = Intent(context, AnadirTratamientoActivity::class.java)
                startActivity(nav)
               // val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
               // val usuarioID = sharedPref.getInt("actualUserID", -1)
               // var tratamiento = Tratamiento(0,"Tratamiento desde fragment",usuarioID,2 )
               // tratamientoViewModel.insert(tratamiento)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
