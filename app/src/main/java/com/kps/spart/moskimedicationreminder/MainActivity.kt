package com.kps.spart.moskimedicationreminder

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_detalles_medicamento.*

class MainActivity : AppCompatActivity() {
    private var currentSectionID: Int = 0
     var currentDirectoryID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        title = getString(R.string.app_name)


        val bnve = findViewById<BottomNavigationViewEx>(R.id.bn_principal)
        bnve.enableAnimation(true)
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)

        currentDirectoryID = savedInstanceState?.getInt("currentDirectoryId") ?: 0



        bnve.setOnNavigationItemSelectedListener { item ->
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            when(item.itemId){
                R.id.navigation_home ->{
                    add_element_fab.hide()
                }else->{
                add_element_fab.show()
                }
            }

            when (item.itemId) {
                R.id.navigation_home -> {
                    transaction.replace(R.id.content, HoyFragment(), getString(R.string.fragmentoHoy)).commit()
                    currentSectionID = item.itemId
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_tratamientos -> {
                    transaction.replace(R.id.content, TratamientosFragment(), getString(R.string.fragmentoTratamientos)).commit()
                    currentSectionID = item.itemId
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_medicamentos -> {
                    transaction.replace(R.id.content, MedicamentosFragment(), getString(R.string.fragmentoMedicamentos)).commit()
                    currentSectionID = item.itemId
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_citas -> {
                    transaction.replace(R.id.content, CitasMedicasFragment(), getString(R.string.fragmentoCitas)).commit()
                    currentSectionID = item.itemId
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_directorio -> {
                    val directorioFragment = DirectorioFragment()

                    transaction.replace(R.id.content, directorioFragment, getString(R.string.fragmentoDirectorio)).commit()
                    currentSectionID = item.itemId
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
            }
            R.id.navigation_tratamientos -> {
                transaction.replace(R.id.content, TratamientosFragment(), getString(R.string.fragmentoTratamientos)).commit()
            }
            R.id.navigation_medicamentos -> {
                transaction.replace(R.id.content, MedicamentosFragment(), getString(R.string.fragmentoMedicamentos)).commit()
            }
            R.id.navigation_citas -> {
                transaction.replace(R.id.content, CitasMedicasFragment(), getString(R.string.fragmentoCitas)).commit()
            }
            R.id.navigation_directorio -> {
                val directorioFragment = DirectorioFragment()
                transaction.replace(R.id.content, directorioFragment, getString(R.string.fragmentoDirectorio)).commit()
            }

            else ->{
                transaction.replace(R.id.content, HoyFragment(), getString(R.string.fragmentoHoy)).commit()
            }
        }



        //Creamos el evento que escucha los cambios en el navigation drawer
        nav_view.setNavigationItemSelectedListener { menuItem ->

            //menuItem.isChecked = true

            drawer_layout.closeDrawers()

          //   Toast.makeText(this,"Item seleccionado: " + menuItem.title, Toast.LENGTH_SHORT).show()

            when (menuItem.itemId) {
                R.id.nav_account -> {
                    val nav = Intent(this@MainActivity, DetallesPerfilActivity::class.java)
                    startActivity(nav)
                }

                R.id.nav_change_user -> {
                    val nav = Intent(this@MainActivity, ListarUsuariosActivity::class.java)
                    startActivity(nav)
                }

                R.id.nav_settings -> {

                }

            }


            true
        }

        add_element_fab.setOnClickListener{
            Toast.makeText(this@MainActivity,"Estas tocando el fab", Toast.LENGTH_SHORT).show()
        }

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

}
