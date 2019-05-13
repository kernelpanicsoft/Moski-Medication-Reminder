package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TomaViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class TomasFragment : Fragment() {
    lateinit var RV : RecyclerView
    lateinit var tomaViewModel : TomaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_tomas, container, false)
        RV = v.findViewById(R.id.RecViewTomas)
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        val adapter = DetallesTomaAdapter()
        tomaViewModel = ViewModelProviders.of(this).get(TomaViewModel::class.java)
        tomaViewModel.getTomasTratamiento((activity as DetallesTratamientoActivity).tratamiento_id).observe(this, Observer {
            adapter.submitList(it)
        })


        RV.adapter = adapter

        return v
    }
}