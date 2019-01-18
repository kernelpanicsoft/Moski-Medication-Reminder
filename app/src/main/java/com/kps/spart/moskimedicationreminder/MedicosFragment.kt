package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicoViewModel
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
import kotlinx.android.synthetic.main.fragment_medicos.*

import android.provider.BaseColumns
import model.MMDContract
import model.mmrbd


class MedicosFragment : Fragment() {

    lateinit var medicoViewModel : MedicoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_medicos, container, false)

        RecViewMedicos.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RecViewMedicos.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RecViewMedicos.context, LinearLayout.VERTICAL)
        RecViewMedicos.addItemDecoration(dividerItemDecoration)




        val adapter = MedicosAdapter(context)
        medicoViewModel = ViewModelProviders.of(this).get(MedicoViewModel::class.java)
        medicoViewModel.allMedicos.observe(this, Observer {
            adapter.submitList(it)
        })
        adapter.setOnItemClickListener(View.OnClickListener {
            val nav = Intent(context,DetallesMedicoActivity::class.java)
           // nav.putExtra("MEDIC_ID", adapter.getMedicID(RV.getChildAdapterPosition(it)))
            startActivity(nav)

        })

        RecViewMedicos.adapter = adapter

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
