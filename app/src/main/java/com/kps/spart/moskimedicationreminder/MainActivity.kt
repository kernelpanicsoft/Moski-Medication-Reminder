package com.kps.spart.moskimedicationreminder

import MMR.viewModels.UsuarioViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import elements.Usuario
import kotlinx.android.synthetic.main.nav_header.*


class MainActivity : AppCompatActivity() {
    private var currentSectionID: Int = 0
    var currentDirectoryID: Int = 0
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onStart() {
        super.onStart()
        checkFirstRun()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        title = getString(R.string.title_home)

        ab.elevation = 4.0f

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel::class.java)

        val bnve = findViewById<BottomNavigationViewEx>(R.id.bn_principal)
        bnve.enableAnimation(true)
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)
        bnve.setTextSize(10.0f)

        currentDirectoryID = savedInstanceState?.getInt("currentDirectoryId") ?: 0

        bnve.setOnNavigationItemSelectedListener { item ->
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()


            when (item.itemId) {
                R.id.navigation_home -> {
                    transaction.replace(R.id.content, HoyFragment(), getString(R.string.fragmentoHoy)).commit()
                    currentSectionID = item.itemId
                    title = getString(R.string.title_home)
                    ab.elevation = 4.0f
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_tratamientos -> {
                    transaction.replace(R.id.content, TratamientosFragment(), getString(R.string.fragmentoTratamientos)).commit()
                    currentSectionID = item.itemId
                    title = getString(R.string.title_tratamientos)
                    ab.elevation = 4.0f
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_medicamentos -> {
                    transaction.replace(R.id.content, MedicamentosFragment(), getString(R.string.fragmentoMedicamentos)).commit()
                    currentSectionID = item.itemId
                    title = getString(R.string.title_medicamentos)
                    ab.elevation = 4.0f
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_citas -> {
                    transaction.replace(R.id.content, CitasMedicasFragment(), getString(R.string.fragmentoCitas)).commit()
                    currentSectionID = item.itemId
                    title = getString(R.string.title_citas)
                    ab.elevation = 4.0f
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_directorio -> {
                    val directorioFragment = DirectorioFragment()
                    ab.elevation = 0.0f
                    transaction.replace(R.id.content, directorioFragment, getString(R.string.fragmentoDirectorio)).commit()
                    currentSectionID = item.itemId
                    title = getString(R.string.title_directorio)
                    return@setOnNavigationItemSelectedListener true
                }

            }

            true

        }

        //Cargamos el fragmento inicial Hoy si es que no se ha seleccionado nada con la navegacion en otro caso cargamos el ultimo fragmento
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
            //transaction.replace(R.id.content, HoyFragment(), getString(R.string.fragmentoHoy)).commit()


        currentSectionID = savedInstanceState?.getInt("currentFragmentId") ?: 0


        when (currentSectionID){
            R.id.navigation_home -> {
                transaction.replace(R.id.content, HoyFragment(), getString(R.string.fragmentoHoy)).commit()
                title = getString(R.string.title_home)
            }
            R.id.navigation_tratamientos -> {
                transaction.replace(R.id.content, TratamientosFragment(), getString(R.string.fragmentoTratamientos)).commit()
                title = getString(R.string.title_tratamientos)
            }
            R.id.navigation_medicamentos -> {
                transaction.replace(R.id.content, MedicamentosFragment(), getString(R.string.fragmentoMedicamentos)).commit()
                title = getString(R.string.title_medicamentos)
            }
            R.id.navigation_citas -> {
                transaction.replace(R.id.content, CitasMedicasFragment(), getString(R.string.fragmentoCitas)).commit()
                title = getString(R.string.title_citas)
            }
            R.id.navigation_directorio -> {
                val directorioFragment = DirectorioFragment()
                transaction.replace(R.id.content, directorioFragment, getString(R.string.fragmentoDirectorio)).commit()
                title = getString(R.string.title_directorio)
            }

            else ->{
                transaction.replace(R.id.content, HoyFragment(), getString(R.string.fragmentoHoy)).commit()
                title = getString(R.string.title_home)
            }
        }



        //Creamos el evento que escucha los cambios en el navigation drawer
        nav_view.setNavigationItemSelectedListener { menuItem ->
            drawer_layout.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_account -> {
                    val nav = Intent(this@MainActivity, DetallesPerfilActivity::class.java)
                    nav.putExtra("USER_ID",getCurrentUserID())
                    startActivity(nav)
                }

                R.id.nav_change_user -> {
                    val nav = Intent(this@MainActivity, ListarUsuariosActivity::class.java)
                    startActivity(nav)
                }

                R.id.nav_settings -> {
                    val nav = Intent(this@MainActivity, settingsManager.SettingsActivity::class.java)
                    startActivity(nav)
                }

            }
            true
        }


    }

    override fun onResume() {
        super.onResume()

        usuarioViewModel.getUsuario(getCurrentUserID()).observe(this, Observer {
            updateDrawerLabels(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentFragmentId",currentSectionID)
        outState.putInt("currentDirectoryId",currentDirectoryID)

    }

    fun getCurrentUserID() : Int{
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
         return sharedPref.getInt("actualUserID", -1)

    }

    fun updateDrawerLabels(usuario: Usuario?){
        val header_nav_view = nav_view.getHeaderView(0)
        val nombreUsuarioActual = header_nav_view.findViewById<TextView>(R.id.currentUserNameTV)
        val imagenUsuarioActual = header_nav_view.findViewById<ImageView>(R.id.currentUserIV)
        nombreUsuarioActual.text = usuario?.nombre + " " + usuario?.apellidos

        val valueInPixels = resources.getDimension(R.dimen.listItemImagen)
        if(usuario?.imagen.isNullOrEmpty()){
            imagenUsuarioActual.setImageResource(R.drawable.ic_user)
        }else{
            setPic(usuario?.imagen!!, valueInPixels.toInt(),valueInPixels.toInt(),imagenUsuarioActual)
        }
      /* usuario?.imagen?.let {
           setPic(it, imagenUsuarioActual.width,imagenUsuarioActual.height,imagenUsuarioActual)
           Toast.makeText(this,"La imagen no es null", Toast.LENGTH_SHORT).show()
        }
*/

    }


    private fun setPic(mCurrentPhotoPath : String, targetW: Int, targetH: Int, userPic : ImageView)  {
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(mCurrentPhotoPath,this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }

        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)?.also { bitmap ->
            userPic.setImageBitmap(bitmap)
        }
    }

    private fun checkFirstRun() : Boolean{
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        if(sharedPref.getBoolean("firstrun",true)){
            val builder = AlertDialog.Builder(this)
            builder.setView(R.layout.welcome_dialog)
            builder.setPositiveButton("Entendido"){ _, _ ->
                sharedPref.edit().putBoolean("firstrun",false).apply()
            }
            val dialog = builder.create()
          //  dialog.show()

            val firstRunWelcome = Intent(this,welcome_screen::class.java)
            startActivity(firstRunWelcome)


        }


        return true

    }


}
