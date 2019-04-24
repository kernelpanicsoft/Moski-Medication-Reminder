package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicoViewModel
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
import elements.Medico


class MedicosFragment : Fragment() {

    lateinit var medicoViewModel : MedicoViewModel
    lateinit var RV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_medicos, container, false)

        RV = v.findViewById<RecyclerView>(R.id.RecViewMedicos)
        RV.setHasFixedSize(true)
        return v
    }

    override fun onResume(){
        super.onResume()

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        val adapter = MedicosAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID",-1)
        medicoViewModel = ViewModelProviders.of(this).get(MedicoViewModel::class.java)
        medicoViewModel.getMedicosUsuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
        })
        adapter.setOnItemClickListener(View.OnClickListener {
            val nav = Intent(context,DetallesMedicoActivity::class.java)
            val medicoSeleccionado = adapter.getMedicAt(RV.getChildAdapterPosition(it))
            nav.putExtra("MEDIC_ID", medicoSeleccionado.id)
            startActivity(nav)
        })

        RV.adapter = adapter
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
