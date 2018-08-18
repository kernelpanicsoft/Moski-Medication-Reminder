package model

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

public class ProviderMMR : ContentProvider() {
    lateinit var dbHelper: mmrbd


    companion object Matcher{
        var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init{
            uriMatcher.addURI(MMDContract.columnas.AUTHORITY,MMDContract.columnas.TABLA_FARMACIA,1)
        }

    }

    override fun onCreate(): Boolean {
        dbHelper  =  mmrbd(context)

        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        val db = dbHelper.writableDatabase

        val rowID = db.insert(MMDContract.columnas.TABLA_FARMACIA, null, values)


        val uri_actividad = ContentUris.withAppendedId(MMDContract.columnas.CONTENT_BASE_URI, rowID)

        return uri_actividad



    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}