package com.kps.spart.moskimedicationreminder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import Elementos.Farmacia
import android.content.CursorLoader
import android.database.Cursor
import android.database.DatabaseUtils
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import model.MMDContract
import model.mmrbd


class FarmaciasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_farmacias, container, false)
        val RV = v.findViewById<View>(R.id.RecViewFarmacias) as RecyclerView
        RV.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RV.layoutManager = mLayoutManager

        val dividerItemDecoration = DividerItemDecoration(RV.context, LinearLayout.VERTICAL)
        RV.addItemDecoration(dividerItemDecoration)

        val farmacias = Array(20){Farmacia()}




        val dbHelper = mmrbd(context)
        val db = dbHelper.writableDatabase

        // Filter results WHERE "title" = 'My Title'
        val selection = "${MMDContract.columnas.NOMBRE_FARMACIA} = ?"
        val selectionArgs = arrayOf("My Title")



        val cursor = db.query(
                MMDContract.columnas.TABLA_FARMACIA,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        )

       //  val cursor = db.rawQuery("select * from ${MMDContract.columnas.TABLA_FARMACIA}", null)

     //   Toast.makeText(context,"Datos en el cursor " + cursor.count + " Con datos: " + DatabaseUtils.dumpCursorToString(cursor), Toast.LENGTH_SHORT).show()

       // adapter.swapCursor(cursor)

        val adapter = FarmaciasAdapter(farmacias, cursor)
        adapter.setOnClickListener(View.OnClickListener {
            val nav = Intent(context, DetallesFarmaciaActivity::class.java)
            startActivity(nav)
        })

        RV.adapter = adapter

        Toast.makeText(context,"Tamaño de adaptador: " + adapter.itemCount +  "Tamaño del cursor: " + cursor.count, Toast.LENGTH_SHORT).show()

        return v
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context,MMDContract.columnas.CONTENT_BASE_URI, null, null, null, null) as Loader<Cursor>
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
     // adapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }





}// Required empty public constructor
