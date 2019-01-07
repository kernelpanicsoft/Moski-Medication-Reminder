package com.kps.spart.moskimedicationreminder

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

class AnadirFichaContactoDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflatedView = activity.layoutInflater.inflate(R.layout.list_item_ficha_contacto, null)

        builder.setTitle(getString(R.string.anadir_ficha_de_contacto))
                .setPositiveButton(getString(R.string.guardar), null)
                .setNegativeButton(getString(R.string.cancelar)) { dialog, which ->

                }





        return builder.create()
    }



}