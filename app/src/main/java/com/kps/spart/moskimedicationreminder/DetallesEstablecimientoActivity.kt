package com.kps.spart.moskimedicationreminder

import MMR.viewModels.EstablecimientoViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import elements.Establecimiento
import kotlinx.android.synthetic.main.activity_detalles_establecimiento.*
import model.CodigosDeSolicitud
import java.util.*


class DetallesEstablecimientoActivity : AppCompatActivity() {
    private var establishment_id : Int = -1
    lateinit var establecimientoViewModel : EstablecimientoViewModel
    lateinit var establecimientoActualLive : LiveData<Establecimiento>

    private var iconsCollection: Array<String>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_establecimiento)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detalles_establecimiento)


        establishment_id = intent.getIntExtra("ESTABLISHMENT_ID", -1)
        iconsCollection = this@DetallesEstablecimientoActivity.resources.getStringArray(R.array.tipo_establecimiento)

        establecimientoViewModel = ViewModelProviders.of(this).get(EstablecimientoViewModel::class.java)
        establecimientoActualLive = establecimientoViewModel.getEstablecimiento(establishment_id)
        establecimientoActualLive.observe(this, Observer {
            populateEstablishmentFieldsFromDB(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesEstablecimientoActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    _, which ->
                    when(which){
                        0 ->{
                            val nav = Intent(this@DetallesEstablecimientoActivity,AnadirEstablecimientoActivity::class.java)
                            nav.putExtra("ESTABLISHMENT_ID", establishment_id )
                            startActivityForResult(nav, CodigosDeSolicitud.EDITAR_ESTABLECIMIENTO)
                        }
                        1 ->{
                            val innerBuilder = AlertDialog.Builder(this@DetallesEstablecimientoActivity)
                            innerBuilder.setTitle(getString(R.string.eliminar_establecimiento))
                                    .setMessage(getString(R.string.esta_seguro_que_desea_eliminar_el_establecimiento))
                                    .setPositiveButton(getString(R.string.si)) { dialog, id ->
                                      deleteEstablishment()
                                    }
                                    .setNegativeButton(getString(R.string.no)){ dialog, id ->
                                        Toast.makeText(this@DetallesEstablecimientoActivity,"Estas no eliminas el establecimiento" + id,Toast.LENGTH_SHORT).show()
                                    }

                            val innerDialog = innerBuilder.create()
                            innerDialog.show()

                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()

                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun populateEstablishmentFieldsFromDB(establecimiento: Establecimiento?){
        when(iconsCollection?.indexOf(establecimiento?.tipo)){
            0 -> {iconoEstablecimiento.setImageResource(R.drawable.ic_pharmacy)}
            1 -> {iconoEstablecimiento.setImageResource(R.drawable.ic_medic_lab)}
            2 -> {iconoEstablecimiento.setImageResource(R.drawable.ic_xray_lab)}
            3 -> {iconoEstablecimiento.setImageResource(R.drawable.ic_crutch)}
        }

        nombreEstablecimientoTV.text = establecimiento?.nombre
        tipoEstablecimientoTV.text = establecimiento?.tipo
        direccionEstablecimietoTV.text = establecimiento?.direccion
        Telefono1EstablecimientoTV.text = establecimiento?.telefono1
        Telefono2EstablecimientoTV.text = establecimiento?.telefono2
        EmailEstablecimientoTV.text = establecimiento?.email
        sitioWebEstablecimientoTV.text = establecimiento?.sitioWeb

        if(establecimiento?.latitud?.compareTo(0.0) != 0 || establecimiento?.longitud?.compareTo(0.0) != 0){
            addMapFragment(establecimiento?.latitud!!,establecimiento?.longitud!!)
            abrirEnMapasTV.visibility = View.VISIBLE
            abrirEnMapasTV.setOnClickListener {
                val uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f(%s)", establecimiento.latitud, establecimiento.longitud, "Prueba")
                //val uri = "geo:0,0?q=34.99,-106.61(Treasure)"
                //Toast.makeText(this@DetallesEstablecimientoActivity, "Valor de cadena: " + uri, Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }else{
            abrirEnMapasTV.visibility = View.GONE
            mapaAnadido.visibility = View.GONE
        }

    }

    private fun deleteEstablishment(){
        if(establecimientoActualLive.hasObservers()){
            establecimientoActualLive.removeObservers(this@DetallesEstablecimientoActivity)
                establecimientoViewModel.delete(establecimientoActualLive.value!!)
                finish()
        }

    }


    private fun addMapFragment(lat : Double, lng: Double){
        mapaAnadido.visibility = View.VISIBLE

        val fragmentTransaction = fragmentManager.beginTransaction()

        val mapFragment = MapFragment()
        fragmentTransaction.add(R.id.mapaAnadido, mapFragment)
        fragmentTransaction.commit()

        mapFragment.getMapAsync{
            val markerLocation = LatLng(lat,lng)
            it.addMarker(MarkerOptions().position(markerLocation))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation,15.0f))
            it.uiSettings.isScrollGesturesEnabled = false
        }
    }

}
