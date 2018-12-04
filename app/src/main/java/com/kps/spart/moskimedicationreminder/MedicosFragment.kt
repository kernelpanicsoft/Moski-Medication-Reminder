package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

import Elementos.Medico
import android.provider.BaseColumns
import android.widget.Toast
import model.MMDContract
import model.mmrbd


class MedicosFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_medicos, container, false)
        val RV = v.findViewById<View>(R.id.RecViewMedicos) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)


        val dbHelper = mmrbd(context!!)
        val db = dbHelper.writableDatabase

        val columns = arrayOf(BaseColumns._ID, MMDContract.columnas.TITULO_DOCTOR, MMDContract.columnas.NOMBRE_DOCTOR, MMDContract.columnas.ESPECIALIDAD_DOCTOR, MMDContract.columnas.COLOR_DOCTOR)
        val cursor = db.query(
                MMDContract.columnas.TABLA_DOCTOR,
                columns,
                null,
                null,
                null,
                null,
                null
        )


        val adapter = MedicosAdapter(cursor)
        adapter.setOnItemClickListener(View.OnClickListener {
            val nav = Intent(context,DetallesMedicoActivity::class.java)
            nav.putExtra("MEDIC_ID", adapter.getMedicID(RV.getChildAdapterPosition(it)))
            startActivity(nav)

        })

        RV.adapter = adapter

        return v
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_sort, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                val nav = Intent(context, AnadirTratamientoActivity::class.java)
                startActivity(nav)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}// Required empty public constructor
