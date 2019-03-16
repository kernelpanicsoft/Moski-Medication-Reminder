package com.kps.spart.moskimedicationreminder

import MMR.viewModels.EstablecimientoViewModel
import elements.Establecimiento
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_anadir_establecimiento.*
import java.lang.Exception


class AnadirEstablecimientoActivity : AppCompatActivity() {
    lateinit var establecimientoViewModel : EstablecimientoViewModel
    private lateinit var  establecimientoActualLive : LiveData<Establecimiento>

   // lateinit var establecimiento: Establecimiento
    var tipoEstablecimiento : String = ""
    var latitud : Double = 0.0
    var longitud : Double = 0.0
    var mNombre : String? = null
    var mDireccion : String? = null
    var mTelefono1 : String? = null
    var mTelefono2 : String? = null
    var mEmail : String? = null
    var mSitioWeb : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_establecimiento)


        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        establecimientoViewModel = ViewModelProviders.of(this@AnadirEstablecimientoActivity).get(EstablecimientoViewModel::class.java)

        if(intent.hasExtra("ESTABLISHMENT_ID")){
            title = getString(R.string.editar_establecimiento)
            establecimientoActualLive = establecimientoViewModel.getEstablecimiento(intent.getIntExtra("ESTABLISHMENT_ID", -1))
            establecimientoActualLive.observe(this, android.arch.lifecycle.Observer {
             //   Toast.makeText(this@AnadirEstablecimientoActivity, "Datos del establecimiento live: " + establecimientoActualLive.value?.nombre,Toast.LENGTH_SHORT).show()

                NombreEstablecimientoTV.setText(it?.nombre,TextView.BufferType.EDITABLE)
                DireccionEstablecimientoTV.setText(it?.direccion,TextView.BufferType.EDITABLE)
                Telefono1EstablecimientoTV.setText(it?.telefono1,TextView.BufferType.EDITABLE)
                Telefono2EstablecimientoTV.setText(it?.telefono2,TextView.BufferType.EDITABLE)
                EmailEstablecimientoTV.setText(it?.email,TextView.BufferType.EDITABLE)
                SitioWebEstablecimeintoTV.setText(it?.sitioWeb,TextView.BufferType.EDITABLE)

                val EstablishmentTypeIndex = this.resources.getStringArray(R.array.tipo_establecimiento).indexOf(it?.tipo)
                SpinnerTipoEstablecimiento.setSelection(EstablishmentTypeIndex)

                latitud = it?.latitud!!
                longitud = it?.longitud!!
                if(latitud != 0.0 && longitud != 0.0){
                    addMapFragment(latitud,longitud)
                    anadirLocationButton.text = getString(R.string.eliminar_ubicacion)
                }

            })

        }else{
            title = getString(R.string.anadir_establecimiento)
        }

        testFab.setOnClickListener {
            Toast.makeText(this@AnadirEstablecimientoActivity, "Datos del establecimiento live: " + establecimientoActualLive.value?.nombre,Toast.LENGTH_SHORT).show()
        }





        SpinnerTipoEstablecimiento.adapter = ArrayAdapter(this@AnadirEstablecimientoActivity, android.R.layout.simple_spinner_dropdown_item,this.resources.getStringArray(R.array.tipo_establecimiento))
        SpinnerTipoEstablecimiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipoEstablecimiento = parent?.getItemAtPosition(position).toString()
                when(position){
                    0 -> {iconoEstablecimientoIV.setImageResource(R.drawable.ic_pharmacy)}
                    1 -> {iconoEstablecimientoIV.setImageResource(R.drawable.ic_medic_lab)}
                    2 -> {iconoEstablecimientoIV.setImageResource(R.drawable.ic_xray_lab)}
                    3 -> {iconoEstablecimientoIV.setImageResource(R.drawable.ic_crutch)}
                }
            }
        }
        anadirLocationButton.setOnClickListener{
            if(latitud == 0.0 && longitud == 0.0) {
                val nav = Intent(this@AnadirEstablecimientoActivity, MapsActivity::class.java)
                startActivityForResult(nav, 6832)
            }else{
                disableMapFragment()
            }
        }
        //Si latitud o longitud != null o != 0.0 añade el mapa en rotación de pantalla
        try{
            latitud = savedInstanceState?.getDouble("latitud")!!
            longitud = savedInstanceState.getDouble("longitud")
            if(latitud != 0.0 && longitud != 0.0){
                addMapFragment(latitud,longitud)
            }


        }catch (e : Exception){}

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 6832){
            if(resultCode == Activity.RESULT_OK){
                latitud = data?.getDoubleExtra("lat",0.0)!!
                longitud = data.getDoubleExtra("lng", 0.0)
                addMapFragment(latitud,longitud)
                anadirLocationButton.text = getString(R.string.eliminar_ubicacion)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val establecimiento : Establecimiento

        if(intent.hasExtra("ESTABLISHMENT_ID")){
            establecimiento = establecimientoActualLive.value!!
        }else{
            establecimiento = Establecimiento(0)
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirEstablecimientoActivity)

            val usuarioID = sharedPref.getInt("actualUserID",-1)
            establecimiento.usuarioID = usuarioID

        }
        when (item.itemId) {
            R.id.itemSave -> {
                establecimiento.nombre  = NombreEstablecimientoTV.text.toString()
                establecimiento.tipo = tipoEstablecimiento
                establecimiento.direccion = DireccionEstablecimientoTV.text.toString()
                establecimiento.telefono1 = Telefono1EstablecimientoTV.text.toString()
                establecimiento.telefono2 = Telefono2EstablecimientoTV.text.toString()
                establecimiento.email = EmailEstablecimientoTV.text.toString()
                establecimiento.sitioWeb = SitioWebEstablecimeintoTV.text.toString()
                establecimiento.latitud = latitud
                establecimiento.longitud = longitud


                if(establecimiento.usuarioID != -1){

                    saveEstablishmentToDB(establecimiento)
                }

                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
    //Añade un fragmento que muestra un mapa con un marcador indicando la ubicacion ingresada por el usuario
    private fun addMapFragment(lat : Double, lng: Double){
        mapaAnadido.visibility = View.VISIBLE

        val fragmentTransaction = fragmentManager.beginTransaction()

        val mapFragment = MapFragment()
        fragmentTransaction.add(R.id.mapaAnadido,mapFragment)
        fragmentTransaction.commit()

        mapFragment.getMapAsync {
            val markerLocation = LatLng(lat, lng)
            it.addMarker(MarkerOptions().position(markerLocation))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 15.0f))
            it.uiSettings.isScrollGesturesEnabled = false
        }
    }

    //Elimina el fragmento especificado en el punto anterior y restablece la ubicación en (0,0)
    private fun disableMapFragment(){
        val mapFragment = fragmentManager.findFragmentById(R.id.mapaAnadido)
        if(mapFragment != null){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(mapFragment).commit()
        }
        mapaAnadido.visibility = View.GONE
        latitud = 0.0
        longitud = 0.0
        anadirLocationButton.text=getString(R.string.a_adir_ubicaci_n_en_mapa)
    }


    private fun saveEstablishmentToDB(establecimiento: Establecimiento){
        if(intent.hasExtra("ESTABLISHMENT_ID")){
            establecimientoViewModel.update(establecimiento)
        }else{
            establecimientoViewModel.insert(establecimiento)
        }

        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.run {
            putDouble("latitud", latitud)
            putDouble("longitud", longitud)
            putString("nombreEstablecimientoActualizado", NombreEstablecimientoTV.text.toString() )
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.run {
            mNombre = getString("nombreEstablecimientoActualizado")
            latitud = getDouble("latitud")
            longitud = getDouble("longitud")

            if(latitud != 0.0 && longitud != 0.0){
                anadirLocationButton.text = getString(R.string.eliminar_ubicacion)
            }
        }
    }





}
