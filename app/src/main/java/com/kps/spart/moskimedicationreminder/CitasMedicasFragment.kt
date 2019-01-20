package com.kps.spart.moskimedicationreminder

import MMR.viewModels.CitaMedicaViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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

import model.MMDContract
import model.mmrbd


class CitasMedicasFragment : Fragment() {
    lateinit var citaMedicasViewModel : CitaMedicaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_citas_medicas, container, false)
        val RV = v.findViewById<View>(R.id.RecViewCitas) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)



        val adapter = CitasAdapter(context)
        citaMedicasViewModel = ViewModelProviders.of(this).get(CitaMedicaViewModel::class.java)
        citaMedicasViewModel.allCitasMedicas.observe(this, Observer{
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesCitaMedicaActivity::class.java)

            startActivity(nav)

        })

        RV.adapter = adapter
        // Inflate the layout for this fragment
        return v
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
