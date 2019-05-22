package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TomaViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import elements.Toma
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
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
import android.widget.Toast


class HoyFragment : Fragment() {

    lateinit var tomaViewModel : TomaViewModel
    lateinit var RV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.actionBar?.title = "Horario"


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_hoy, container, false)

        RV = v.findViewById<RecyclerView>(R.id.RecViewHoy)
        RV.setHasFixedSize(true)



        return v
    }

    override fun onResume() {
        super.onResume()

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID",-1)


        tomaViewModel = ViewModelProviders.of(this).get(TomaViewModel::class.java)
        val adapter = HorarioAdapter(context, tomaViewModel)
        tomaViewModel.getTomasDelDiaUsusuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
        })

        adapter.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle(getString(R.string.acciones_de_toma))
                    .setItems(R.array.acciones_toma){ dialog, which ->
                        when(which){
                            1 -> {
                                adapter.changeShotStatus(RV.getChildAdapterPosition(it),1)
                            }
                            2 -> {
                                adapter.changeShotStatus(RV.getChildAdapterPosition(it),2)
                            }
                            3 -> {
                                adapter.changeShotStatus(RV.getChildAdapterPosition(it),3)
                            }
                        }
                    }

            val dialog = builder.create()
            dialog.show()
        })

        RV.adapter = adapter
    }



    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_sort, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemFilter -> {
                Toast.makeText(context, "Estás haciendo click en el icono sort", Toast.LENGTH_SHORT).show()


                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}// Required empty public constructor
