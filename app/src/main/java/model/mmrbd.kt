package model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.provider.BaseColumns

/**
 * Created by spart on 11/03/2018.
 */

private const val SQL_CREATE_ENTRIES: String =  "CREATE TABLE ${MMDContract.columnas.TABLA_FARMACIA} (" +
                                                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                "${MMDContract.columnas.NOMBRE_FARMACIA} TEXT," +
                                                "${MMDContract.columnas.DIRECCION_FARMACIA} TEXT," +
                                                "${MMDContract.columnas.TELEFONO1_FARMACIA} TEXT," +
                                                "${MMDContract.columnas.TELEFONO2_FARMACIA} TEXT," +
                                                "${MMDContract.columnas.EMAIL_FARMACIA} TEXT," +
                                                "${MMDContract.columnas.WEB_FARMACIA} TEXT," +
                                                "${MMDContract.columnas.LATITUD_FARMACIA} REAL," +
                                                "${MMDContract.columnas.LONGITUD_FARMACIA} REAL)"

private const val SQL_DELETE_ENTRIES: String =  "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_FARMACIA}"



class mmrbd(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }



    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MMR.db"
    }
}
