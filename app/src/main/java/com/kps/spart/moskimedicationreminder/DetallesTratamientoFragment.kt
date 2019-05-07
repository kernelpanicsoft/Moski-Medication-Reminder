package com.kps.spart.moskimedicationreminder

import MMR.viewModels.MedicamentoViewModel
import MMR.viewModels.TratamientoViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import elements.Medicamento
import elements.Tratamiento

class DetallesTratamientoFragment : Fragment() {
    lateinit var tratamientoViewModel: TratamientoViewModel
    lateinit var tratamientoActualLive: LiveData<Tratamiento>

    lateinit var medicamentoViewModel: MedicamentoViewModel
    lateinit var mecidamentoActualLive: LiveData<Medicamento>

    var TituloTratamientoTV : TextView? = null
    var IndicacionesTratamientoTV : TextView? = null
    var medicamentoTratamientoTV : TextView? = null
    var IconoMedicamento : ImageView? = null
    var NotasMedicamentoTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      //  Toast.makeText(context, "ID de tratamiento desde fragment: " + (activity as DetallesTratamientoActivity).tratamiento_id, Toast.LENGTH_SHORT).show()
        val v = inflater.inflate(R.layout.fragment_detalles_tratamiento, container, false)

        tratamientoViewModel = ViewModelProviders.of(this).get(TratamientoViewModel::class.java)
        tratamientoActualLive = tratamientoViewModel.getTratamiento((activity as DetallesTratamientoActivity).tratamiento_id)

        TituloTratamientoTV = v.findViewById(R.id.TituloTratamientoTV)
        IndicacionesTratamientoTV = v.findViewById(R.id.IndicacionesTratamientoTV)
        medicamentoTratamientoTV = v.findViewById(R.id.medicamentoTratamientoTV)
        IconoMedicamento = v.findViewById(R.id.IconoMedicamento)
        NotasMedicamentoTV = v.findViewById(R.id.NotasMedicamentoTV)

        tratamientoActualLive.observe(this, Observer {
            populateTreatmentFieldsFromDB(it)
        })

        return v
    }



    private fun populateTreatmentFieldsFromDB(tratamiento: Tratamiento?){
        TituloTratamientoTV?.text = tratamiento?.titulo
        IndicacionesTratamientoTV?.text = tratamiento?.indicaciones

        medicamentoViewModel = ViewModelProviders.of(this).get(MedicamentoViewModel::class.java)


        val iconsCollection = resources?.getStringArray(R.array.TipoMedicamento)
        medicamentoViewModel.getMedicamento(tratamiento?.medicamentoID!!).observe(this, Observer {
            medicamentoTratamientoTV?.text = it?.nombreMedicamento
            val medicineType = it?.tipo
            val iconsCollection = resources?.getStringArray(R.array.TipoMedicamento)
            when(iconsCollection?.indexOf(medicineType)){
                0 -> {IconoMedicamento?.setImageResource(R.drawable.ic_roundpill)}
                1 -> {IconoMedicamento?.setImageResource(R.drawable.ic_tab)}
                2 -> {IconoMedicamento?.setImageResource(R.drawable.ic_capsula)}
                3 -> {IconoMedicamento?.setImageResource(R.drawable.ic_syrup)}
                4 -> {IconoMedicamento?.setImageResource(R.drawable.ic_drops)}
                5 -> {IconoMedicamento?.setImageResource(R.drawable.ic_eyedrops)}
                6 -> {IconoMedicamento?.setImageResource(R.drawable.ic_ointment)}
                7 -> {IconoMedicamento?.setImageResource(R.drawable.ic_powder)}
                8 -> {IconoMedicamento?.setImageResource(R.drawable.ic_gel)}
                9 -> {IconoMedicamento?.setImageResource(R.drawable.ic_inhalator)}
                10-> {IconoMedicamento?.setImageResource(R.drawable.ic_suppository)}
                11-> {IconoMedicamento?.setImageResource(R.drawable.ic_intravenous)}
                12-> {IconoMedicamento?.setImageResource(R.drawable.ic_syringe)}
            }

            IconoMedicamento?.setColorFilter(it?.color!!)
            NotasMedicamentoTV?.text = it?.nota

        })
    }

}