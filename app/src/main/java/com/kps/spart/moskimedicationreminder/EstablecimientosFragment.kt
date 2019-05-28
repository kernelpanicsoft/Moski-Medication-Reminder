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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import elements.Establecimiento



class EstablecimientosFragment : Fragment() {

    lateinit var establecimientoViewModel : EstablecimientoViewModel
    lateinit var RV: RecyclerView

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



        val adapter = EstablecimientoAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)
        establecimientoViewModel = ViewModelProviders.of(this).get(EstablecimientoViewModel::class.java)
        establecimientoViewModel.getEstablecimientosUsuario(usuarioID).observe(this, Observer<List<Establecimiento>>{
            adapter.submitList(it)
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

}// Required empty public constructor
