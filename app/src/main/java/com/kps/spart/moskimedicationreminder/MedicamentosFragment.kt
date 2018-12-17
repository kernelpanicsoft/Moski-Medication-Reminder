package com.kps.spart.moskimedicationreminder

import android.content.Intent
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

import Elementos.Medicamento
import model.MMDContract
import model.mmrbd


class MedicamentosFragment : Fragment() {


    // TODO: Rename and change types and number of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_medicamentos, container, false)

        val RV = v.findViewById<View>(R.id.RecViewMedicamentos) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        val dbHelper = mmrbd(context!!)
        val db = dbHelper.writableDatabase

        val cursor = db.query(MMDContract.columnas.TABLA_MEDICAMENTO,
                null,
                null,
                null,
                null,
                null,
                null)

        val adapter = MedicamentosAdapter(context,cursor)


        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesMedicamentoActivity::class.java)
            nav.putExtra("MEDICINE_ID", adapter.getMedicineID(RV.getChildAdapterPosition(it)))
            startActivity(nav)
        })

        RV.adapter = adapter


        return v
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_add, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                val nav = Intent(context, AnadirMedicamentoActivity::class.java)
                startActivity(nav)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}// Required empty public constructor
