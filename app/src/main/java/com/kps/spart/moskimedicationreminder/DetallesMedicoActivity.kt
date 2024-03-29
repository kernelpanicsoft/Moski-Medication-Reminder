package com.kps.spart.moskimedicationreminder

import MMR.viewModels.FichaContactoViewModel
import MMR.viewModels.MedicamentoViewModel
import MMR.viewModels.MedicoViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import elements.FichaContacto
import elements.Medico
import kotlinx.android.synthetic.main.activity_detalles_medico.*
import model.MMDContract


class DetallesMedicoActivity : AppCompatActivity() {
    private var medic_id : Int = -1
    lateinit var medicoViewModel : MedicoViewModel
    lateinit var medicoActualLive : LiveData<Medico>
    lateinit var iconsCollection : Array<String>

    lateinit var fichasContactoViewModel : FichaContactoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_medico)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detalles_de_medico)

        iconsCollection = this@DetallesMedicoActivity.resources?.getStringArray(R.array.especialidades)!!

        medic_id = intent.getIntExtra("MEDIC_ID", -1)

        medicoViewModel = ViewModelProviders.of(this).get(MedicoViewModel::class.java)

        medicoActualLive = medicoViewModel.getMedico(medic_id)
        medicoActualLive.observe(this, Observer {
            populateMedicFieldFromDB(it)
            populateMedicContactCards(it)

        })



    }

    private fun populateMedicFieldFromDB(medico : Medico?) {
            TituloDoctorTV.text = medico?.titulo
            NombreDoctorTV.text = medico?.nombre
            EspecialidadTV.text = medico?.especialidad
            when(iconsCollection.indexOf(medico?.especialidad)){
                0 -> { iconoMedicoIV.setImageResource(R.drawable.ic_doctor)}
                1 -> { iconoMedicoIV.setImageResource(R.drawable.ic_alergologo)}
                2 -> { iconoMedicoIV.setImageResource(R.drawable.ic_anestesiologo)}
                3 -> { iconoMedicoIV.setImageResource(R.drawable.ic_angiologo)}
                4 -> { iconoMedicoIV.setImageResource(R.drawable.ic_cardiologo)}
                5 -> { iconoMedicoIV.setImageResource(R.drawable.ic_dermatologo)}
                6 -> { iconoMedicoIV.setImageResource(R.drawable.ic_endocrinologo)}
                7 -> { iconoMedicoIV.setImageResource(R.drawable.ic_fisioterapeuta)}
                8 -> { iconoMedicoIV.setImageResource(R.drawable.ic_gastroenterologo)}
                9 -> { iconoMedicoIV.setImageResource(R.drawable.ic_geriatra)}
                10 -> { iconoMedicoIV.setImageResource(R.drawable.ic_ginecologo)}
                11 -> { iconoMedicoIV.setImageResource(R.drawable.ic_hematologo)}
                12 -> { iconoMedicoIV.setImageResource(R.drawable.ic_homeopata)}
                13 -> { iconoMedicoIV.setImageResource(R.drawable.ic_infectologo)}
                14 -> { iconoMedicoIV.setImageResource(R.drawable.ic_internista)}
                15 -> { iconoMedicoIV.setImageResource(R.drawable.ic_inmunologo)}
                16 -> { iconoMedicoIV.setImageResource(R.drawable.ic_nefrologo)}
                17 -> { iconoMedicoIV.setImageResource(R.drawable.ic_neumologo)}
                18 -> { iconoMedicoIV.setImageResource(R.drawable.ic_neurologo)}
                19 -> { iconoMedicoIV.setImageResource(R.drawable.ic_nutriologo)}
                20 -> { iconoMedicoIV.setImageResource(R.drawable.ic_oftalmologo)}
                21 -> { iconoMedicoIV.setImageResource(R.drawable.ic_ortopedista)}
                22 -> { iconoMedicoIV.setImageResource(R.drawable.ic_otorrinolaringologo)}
                23 -> { iconoMedicoIV.setImageResource(R.drawable.ic_pediatra)}
                24 -> { iconoMedicoIV.setImageResource(R.drawable.ic_psiquiatra)}
                25 -> { iconoMedicoIV.setImageResource(R.drawable.ic_proctologo)}
                26 -> { iconoMedicoIV.setImageResource(R.drawable.ic_radiologo)}
                27 -> { iconoMedicoIV.setImageResource(R.drawable.ic_reumatologo)}
                28 -> { iconoMedicoIV.setImageResource(R.drawable.ic_urologo)}
                29 -> { iconoMedicoIV.setImageResource(R.drawable.ic_traumatologo)}
            }

    }

    private fun populateMedicContactCards(medico: Medico?){
        RecViewFichasContactoMedico.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this@DetallesMedicoActivity,LinearLayoutManager.VERTICAL, false)
        RecViewFichasContactoMedico.layoutManager = mLayoutManager

        fichasContactoViewModel = ViewModelProviders.of(this).get(FichaContactoViewModel::class.java)

        val adapter = FichasContactoAdapter(this@DetallesMedicoActivity,fichasContactoViewModel, medicoViewModel)

        fichasContactoViewModel.getFichasContacto(medic_id).observe(this, Observer<List<FichaContacto>> {
            adapter.submitList(it)
        })

        adapter.setCurrentMedic(medico!!)
        RecViewFichasContactoMedico.adapter = adapter

    }




    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        menuInflater.inflate(R.menu.menu_edit_add_card,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        when(item.itemId){
            R.id.edit_item ->{
                val builder = AlertDialog.Builder(this@DetallesMedicoActivity)
                builder.setItems(R.array.dialogo_editar_eliminar){
                    dialog, which ->
                    when(which){
                        0 ->{
                            val nav = Intent(this@DetallesMedicoActivity, AnadirMedicoActivity::class.java)
                            nav.putExtra("MEDIC_ID", medic_id)
                            startActivityForResult(nav, 683)
                        }
                        1 ->{
                            val innerbuilder = AlertDialog.Builder(this@DetallesMedicoActivity)
                            innerbuilder.setTitle(getString(R.string.eliminar_medico))
                                    .setMessage(getString(R.string.esta_seguro_que_desea_eliminar_al_medico))
                                    .setPositiveButton(getString(R.string.si)){
                                        _, _ ->
                                        deleteMedic()
                                    }
                                    .setNegativeButton(getString(R.string.no)){
                                        _, _ ->

                                    }
                            val innerDialog = innerbuilder.create()
                            innerDialog.show()
                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()
                return true
            }
            R.id.add_contact_card ->{
                val addContactCardNav = Intent(this@DetallesMedicoActivity, AnadirFichaContactoActivity::class.java)
                addContactCardNav.putExtra("MEDIC_ID", medic_id)
                startActivity(addContactCardNav)
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteMedic(){
        if(medicoActualLive.hasObservers()){
            medicoActualLive.removeObservers(this@DetallesMedicoActivity)
            medicoViewModel.delete(medicoActualLive.value!!)

        }
    }


}
