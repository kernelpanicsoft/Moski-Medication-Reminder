package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
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

import model.MMDContract


class CitasMedicasFragment : Fragment() {
    lateinit var citaMedicasViewModel : CitaMedicaViewModel
    private var cita_id : Int = -1

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

        val adapter = CitasAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)
        citaMedicasViewModel = ViewModelProviders.of(this).get(CitaMedicaViewModel::class.java)
        citaMedicasViewModel.getCitasUsuario(usuarioID).observe(this, Observer{
            adapter.submitList(it)
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
        inflater!!.inflate(R.menu.menu_add, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                val nav = Intent(context, AnadirCitaMedicaActivity::class.java)
                startActivity(nav)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}// Required empty public constructor
