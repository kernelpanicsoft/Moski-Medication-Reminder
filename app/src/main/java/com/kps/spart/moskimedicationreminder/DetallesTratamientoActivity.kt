package com.kps.spart.moskimedicationreminder

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalles_tratamiento.*

class DetallesTratamientoActivity : AppCompatActivity() {

     var tratamiento_id = -1


    /*
    lateinit var tratamientoViewModel: TratamientoViewModel
    lateinit var tratamientoActualLive: LiveData<Tratamiento>

    lateinit var medicamentoViewModel: MedicamentoViewModel
    lateinit var mecidamentoActualLive: LiveData<Medicamento>
*/
  //  private var tabLayout: TabLayout? = null
  //  private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_tratamiento)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "Tratamiento"

        tratamiento_id = intent.getIntExtra("TRATAMIENTO_ID", -1)

        setupViewPager(viewPagerTratamiento)
        tabLayoutTratamiento!!.setupWithViewPager(viewPagerTratamiento)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // Toast.makeText(this@DetallesTratamientoActivity,"El id del elemento es: " + item.itemId, Toast.LENGTH_SHORT).show()
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this)
                    builder.setItems(R.array.dialogo_editar_eliminar) { _, which ->
                        when(which){
                            0->{
                                val innerBuilderEdit = AlertDialog.Builder(this)
                                innerBuilderEdit.setTitle(R.string.editar_elemento)
                                innerBuilderEdit.setItems(R.array.dialogo_editar_tratamiento){_, innerWhich ->
                                    when(innerWhich){
                                        0 ->{
                                            val editTreatment = Intent(this, AnadirTratamientoActivity::class.java)
                                            startActivity(editTreatment)
                                        }
                                        1 ->{

                                            val addShots = Intent(this,AnadirTomasActivity::class.java)
                                            val bundle : Bundle = Bundle()

                                            addShots.putExtra("TREATMENT_ID", tratamiento_id.toLong())
                                            Log.d("IDDETALLES", tratamiento_id.toString())
                                            startActivity(addShots)
                                        }
                                    }
                                }
                                val innerDialog = innerBuilderEdit.create()
                                innerDialog.show()

                            }
                            1->{
                                val innerBuilderDelete = AlertDialog.Builder(this)
                                innerBuilderDelete.setTitle(getString(R.string.esta_seguro_eliminar_tratamiento))
                                innerBuilderDelete.setMessage(getString(R.string.mensaje_eliminar_tratamiento_dialogo))
                                innerBuilderDelete.setPositiveButton(getString(R.string.eliminar)){ dialog, id ->
                                    val adapter : ViewPagerAdapter = viewPagerTratamiento.adapter as ViewPagerAdapter
                                    val fragment : DetallesTratamientoFragment = adapter.getItem(0) as DetallesTratamientoFragment
                                    fragment.deleteTreatment()
                                }
                                innerBuilderDelete.setNegativeButton(getString(R.string.cancelar)){
                                    _, wich ->
                                }
                                val alertDialog = innerBuilderDelete.create()
                                alertDialog.show()
                            }
                        }
                    }

                val alertDialog = builder.create()
                alertDialog.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(pager: ViewPager?){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DetallesTratamientoFragment(),getString(R.string.detalles))
        adapter.addFragment(TomasFragment(),getString(R.string.tomas))
        pager?.adapter = adapter
    }

    private inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int) : Fragment{
            return mFragmentList[position]
        }

        override fun getCount(): Int{
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String){
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence{
            return mFragmentTitleList[position]
        }

    }


}

