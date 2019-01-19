package com.kps.spart.moskimedicationreminder

import MMR.viewModels.EstablecimientoViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import elements.Establecimiento

import model.MMDContract
import model.mmrbd


class EstablecimientosFragment : Fragment() {

    lateinit var establecimientoViewModel : EstablecimientoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_farmacias, container, false)
        val RV = v.findViewById<View>(R.id.RecViewFarmacias) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)



        val adapter = EstablecimientoAdapter(context)
        establecimientoViewModel = ViewModelProviders.of(this).get(EstablecimientoViewModel::class.java)
        establecimientoViewModel.allEstablecimientos.observe(this, Observer<List<Establecimiento>>{
            adapter.submitList(it)
        })

        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesEstablecimientoActivity::class.java)

            startActivity(nav)
        })

        RV.adapter = adapter

      //  establecimientoViewModel.insert(Establecimiento(0,"Prueba","Farmacia","NA", "232423232", "23232323", "we@fd.com","www.com.cm",2.9,23.0, 1))
        return v
    }

}// Required empty public constructor
