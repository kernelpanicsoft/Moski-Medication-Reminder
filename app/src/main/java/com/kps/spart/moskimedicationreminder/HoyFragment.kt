package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TomaViewModel
import MMR.viewModels.TratamientoViewModel
import alarms.AlarmHelper
import alarms.NotificationsManager
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
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import model.EstatusToma
import android.media.RingtoneManager
import android.media.Ringtone
import java.util.*


class HoyFragment : Fragment() {

    lateinit var tomaViewModel : TomaViewModel
    lateinit var tratamientoViewModel: TratamientoViewModel
    lateinit var RV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.actionBar?.title = "Horario"


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_hoy, container, false)

        RV = v.findViewById(R.id.RecViewHoy)
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
        tratamientoViewModel = ViewModelProviders.of(this).get(TratamientoViewModel::class.java)
        val adapter = HorarioAdapter(context, tomaViewModel,tratamientoViewModel)
        tomaViewModel.getTomasDelDiaUsusuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
        })

        adapter.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle(getString(R.string.acciones_de_toma))
                    .setItems(R.array.acciones_toma){ dialog, which ->
                        when(which){
                            0 -> {
                                adapter.changeShotStatus(RV.getChildAdapterPosition(it),EstatusToma.TOMADA)
                                Log.d("EstatusEnHoyFragment", EstatusToma.TOMADA.toString())
                            }
                            1 -> {
                                adapter.changeShotStatus(RV.getChildAdapterPosition(it),EstatusToma.PASADA)
                                Log.d("EstatusEnHoyFragment", EstatusToma.PASADA.toString())
                            }
                            2 -> {
                                adapter.changeShotStatus(RV.getChildAdapterPosition(it),EstatusToma.POSPUESTA)
                                Log.d("EstatusEnHoyFragment", EstatusToma.POSPUESTA.toString())
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
                val alarmHelper = AlarmHelper(context!!)
                alarmHelper.createAlarmForNotifications(18,52, "Prueba", "DE notificacion", 1047)

                Toast.makeText(context,"Alarma fijada", Toast.LENGTH_SHORT).show()

               // tomaViewModel.scheduleShotsNotifications()


                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}// Required empty public constructor
