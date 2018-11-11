package com.kps.spart.moskimedicationreminder

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
import android.widget.Toast

import Elementos.Establecimiento
import model.MMDContract
import model.mmrbd


class EstablecimientosFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_farmacias, container, false)
        val RV = v.findViewById<View>(R.id.RecViewFarmacias) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        val farmacias = Array(20){Establecimiento()}


        val dbHelper = mmrbd(context!!)
        val db = dbHelper.writableDatabase

        // Filter results WHERE "title" = 'My Title'
        val selection = "${MMDContract.columnas.NOMBRE_ESTABLECIMIENTO} = ?"
        val selectionArgs = arrayOf("My Title")



        val cursor = db.query(
                MMDContract.columnas.TABLA_ESTABLECIMIENTO,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        )



        val adapter = EstablecimientoAdapter(cursor)
        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesFarmaciaActivity::class.java)
            startActivity(nav)
        })

        RV.adapter = adapter


        return v
    }








}// Required empty public constructor