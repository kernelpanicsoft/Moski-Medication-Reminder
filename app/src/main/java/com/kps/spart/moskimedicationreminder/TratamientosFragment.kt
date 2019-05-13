package com.kps.spart.moskimedicationreminder

import MMR.viewModels.TratamientoViewModel
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
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

import elements.Tratamiento
import model.CodigosDeSolicitud


class TratamientosFragment : Fragment() {

    lateinit var tratamientoViewModel : TratamientoViewModel
    lateinit var RV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_tratamientos, container, false)
        RV = v.findViewById<RecyclerView>(R.id.RecViewTratamientos)
        RV.setHasFixedSize(true)


        return v
    }

    override fun onResume() {
        super.onResume()

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)


        val adapter = TratamientosAdapter(context)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val usuarioID = sharedPref.getInt("actualUserID", -1)
        tratamientoViewModel = ViewModelProviders.of(this).get(TratamientoViewModel::class.java)
        tratamientoViewModel.getTratamientosUsuario(usuarioID).observe(this, Observer {
            adapter.submitList(it)
            Toast.makeText(context,"Tamaño lista: " + it?.size, Toast.LENGTH_SHORT).show()
        })
        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesTratamientoActivity::class.java)
            //   val tratamientoSeleccionado = adapter.getTratamientoAt(RV.getChildAdapterPosition(it))
            //   nav.putExtra("TRATAMIENTO_ID", tratamientoSeleccionado.id)
            startActivity(nav)
        })


        RV.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_add, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                val nav = Intent(context, AnadirTratamientoActivity::class.java)
                startActivityForResult(nav,CodigosDeSolicitud.ANADIR_TRATAMIENTO)
               // val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
               // val usuarioID = sharedPref.getInt("actualUserID", -1)
               // var tratamiento = Tratamiento(0,"Tratamiento desde fragment",usuarioID,2 )
               // tratamientoViewModel.insert(tratamiento)
                return true
            }
            R.id.itemSearch ->{
                tratamientoViewModel.getLastID().observe(this, Observer {
                  //  Toast.makeText(context,"Ultimo ID: " + it?.toString(), Toast.LENGTH_LONG).show()
                    val nav = Intent(context,AnadirTomasActivity::class.java)
                    startActivity(nav)
                })
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CodigosDeSolicitud.ANADIR_TRATAMIENTO){
            if(resultCode == Activity.RESULT_OK){
                tratamientoViewModel.getLastID().observe(this, Observer {
                    //Toast.makeText(context,"Ultimo ID: " + it?.toString(), Toast.LENGTH_LONG).show()
                    val addShots = Intent(context,AnadirTomasActivity::class.java)
                    addShots.putExtra("TREATMENT_ID", it)

                    startActivity(addShots)

                })

               // val addShots = Intent(context,AnadirTomasActivity::class.java)
               // startActivity(addShots)
            }
        }
    }

}
