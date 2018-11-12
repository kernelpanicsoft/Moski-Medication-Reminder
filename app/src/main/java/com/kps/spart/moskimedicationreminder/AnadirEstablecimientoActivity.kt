package com.kps.spart.moskimedicationreminder

import Elementos.Establecimiento
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import model.mmrbd
import kotlinx.android.synthetic.main.activity_anadir_establecimiento.*
import model.MMDContract
import java.lang.Exception
import kotlin.math.ln


class AnadirEstablecimientoActivity : AppCompatActivity() {

    lateinit var dbHelper : mmrbd
    lateinit var establecimiento: Establecimiento
    var latitud : Double = 0.0
    var longitud : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_establecimiento)


        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.anadir_establecimiento)

        dbHelper = mmrbd(this@AnadirEstablecimientoActivity)

        establecimiento = Establecimiento()
        establecimiento.nombre

        SpinnerTipoEstablecimiento.adapter = ArrayAdapter(this@AnadirEstablecimientoActivity, android.R.layout.simple_spinner_dropdown_item,this.resources.getStringArray(R.array.tipo_establecimiento))
        SpinnerTipoEstablecimiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                establecimiento.tipo = parent?.getItemAtPosition(position).toString()
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

       // Toast.makeText(this@AnadirEstablecimientoActivity,"Valores recuperados en rotacion: " + latitud + " " + longitud, Toast.LENGTH_SHORT).show()

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
        when (item.itemId) {
            R.id.itemSave -> {
                establecimiento.nombre  = NombreEstablecimientoTV.text.toString()
                establecimiento.direccion = DireccionEstablecimientoTV.text.toString()
                establecimiento.telefono1 = Telefono1EstablecimientoTV.text.toString()
                establecimiento.telefono2 = Telefono2EstablecimientoTV.text.toString()
                establecimiento.email = EmailEstablecimientoTV.text.toString()
                establecimiento.sitioWeb = SitioWebEstablecimeintoTV.text.toString()

                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@AnadirEstablecimientoActivity)

                val usuarioID = sharedPref.getInt("actualUserID",-1)

                if(usuarioID != -1){
                    establecimiento.usuarioID = usuarioID
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
        val db = dbHelper.writableDatabase
        val errorAtInsertion : Long = -1



        val newRowId = db.insert(MMDContract.columnas.TABLA_ESTABLECIMIENTO,null,establecimiento.toContentValues())

        if(newRowId == errorAtInsertion){
            Toast.makeText(this@AnadirEstablecimientoActivity,getString(R.string.error_crear_establecimiento), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@AnadirEstablecimientoActivity, getString(R.string.establecimiento_creado_correctamente), Toast.LENGTH_SHORT).show()
        }

        finish()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putDouble("latitud", latitud)
        outState?.putDouble("longitud", longitud)
    }

    override fun onDestroy(){
        dbHelper.close()
        super.onDestroy()
    }
}
