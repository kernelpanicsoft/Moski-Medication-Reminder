package model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


/**
 * Created by spart on 11/03/2018.
 */

private const val SQL_CREATE_USERS_TABLE = "CREATE TABLE ${MMDContract.columnas.TABLA_USUARIO} (" +
                                                        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                        "${MMDContract.columnas.NOMBRE_USUARIO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.APELLIDOS_USUARIO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.EDAD_USUARIO} INTEGER NOT NULL," +
                                                        "${MMDContract.columnas.GENERO_USUARIO} TEXT NOT NULL, " +
                                                        "${MMDContract.columnas.PASSWORD_USUARIO} TEXT," +
                                                        "${MMDContract.columnas.IMAGEN_USUARIO} TEXT," +
                                                        "${MMDContract.columnas.EMAIL_RECUPERACION} TEXT)"

private const val SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_USUARIO}"

private const val SQL_CREATE_ESTABLISHMENT_TABLE: String =  "CREATE TABLE ${MMDContract.columnas.TABLA_ESTABLECIMIENTO} (" +
                                                        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                        "${MMDContract.columnas.NOMBRE_ESTABLECIMIENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.TIPO_ESTABLECIMIENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.DIRECCION_ESTABLECIMIENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.TELEFONO1_ESTABLECIMIENTO} TEXT," +
                                                        "${MMDContract.columnas.TELEFONO2_ESTABLECIMIENTO} TEXT," +
                                                        "${MMDContract.columnas.EMAIL_ESTABLECIMIENTO} TEXT," +
                                                        "${MMDContract.columnas.WEB_ESTABLECIMIENTO} TEXT," +
                                                        "${MMDContract.columnas.LATITUD_ESTABLECIMIENTO} REAL," +
                                                        "${MMDContract.columnas.LONGITUD_ESTABLECIMIENTO} REAL," +
                                                        "${MMDContract.columnas.USUARIO_ESTABLECIMIENTO_ID} INTEGER," +
                                                        "FOREIGN KEY(${MMDContract.columnas.USUARIO_ESTABLECIMIENTO_ID}) REFERENCES ${MMDContract.columnas.TABLA_USUARIO}(${BaseColumns._ID}) " +
                                                        "ON DELETE CASCADE)"

private const val SQL_DELETE_ESTABLISHMENT_TABLE: String =  "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_ESTABLECIMIENTO}"

private const val SQL_CREATE_MEDICINE_TABLE: String =   "CREATE TABLE ${MMDContract.columnas.TABLA_MEDICAMENTO} (" +
                                                        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                        "${MMDContract.columnas.NOMBRE_COMERCIAL_MEDICAMENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.NOMBRE_GENERICO_MEDICAMENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.DOSIS_MEDICAMENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.NOTA_MEDICAMENTO} TEXT," +
                                                        "${MMDContract.columnas.TIPO_MEDICAMENTO} TEXT," +
                                                        "${MMDContract.columnas.COLOR_MEDICAMENTO} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.FOTOGRAFIA_MEDICAMENTO} TEXT," +
                                                        "${MMDContract.columnas.USUARIO_MEDICAMENTO_ID} INTEGER," +
                                                        "FOREIGN KEY(${MMDContract.columnas.USUARIO_MEDICAMENTO_ID}) REFERENCES ${MMDContract.columnas.TABLA_USUARIO}(${BaseColumns._ID}) " +
                                                        "ON DELETE CASCADE)"


private const val SQL_DELETE_MEDICINE_TABLE: String =   "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_MEDICAMENTO}"

private const val SQL_CREATE_MEDICS_TABLE : String =     "CREATE TABLE ${MMDContract.columnas.TABLA_DOCTOR} (" +
                                                        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                        "${MMDContract.columnas.TITULO_DOCTOR} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.NOMBRE_DOCTOR} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.ESPECIALIDAD_DOCTOR} TEXT NOT NULL,"  +
                                                        "${MMDContract.columnas.COLOR_DOCTOR} TEXT NOT NULL," +
                                                        "${MMDContract.columnas.USUARIO_DOCTOR_ID} INTEGER," +
                                                        "FOREIGN KEY(${MMDContract.columnas.USUARIO_DOCTOR_ID}) REFERENCES ${MMDContract.columnas.TABLA_USUARIO} (${BaseColumns._ID}) " +
                                                        "ON DELETE CASCADE)"

private const val SQL_DELETE_MEDICS_TABLE : String =     "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_DOCTOR}"

private const val SQL_CREATE_CONTACT_CARD_TABLE : String =  "CREATE TABLE ${MMDContract.columnas.TABLA_FICHA_CONTACTO} (" +
                                                            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                            "${MMDContract.columnas.TITULO_FICHA_CONTACTO} TEXT NOT NULL," +
                                                            "${MMDContract.columnas.DIRECCION_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.TELEFONO_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.CELULAR_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.EMAIL_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.WEB_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.ACCESSO_FICHA_CONTACTO} INTEGER," +
                                                            "${MMDContract.columnas.LATITUD_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.LONGITUD_FICHA_CONTACTO} TEXT," +
                                                            "${MMDContract.columnas.DOCTOR_FICHA_CONTACTO_ID} INTEGER," +
                                                            "FOREIGN KEY(${MMDContract.columnas.DOCTOR_FICHA_CONTACTO_ID}) REFERENCES ${MMDContract.columnas.TABLA_DOCTOR} (${BaseColumns._ID}) " +
                                                            "ON DELETE CASCADE)"

private const val SQL_DELETE_CONTACT_CARD_TABLE : String =  "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_FICHA_CONTACTO}"

private const val SQL_CREATE_APPOINTMENTS_TABLE : String =  "CREATE TABLE ${MMDContract.columnas.TABLA_CITA} (" +
                                                            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                                            "${MMDContract.columnas.TITULO_CITA} TEXT NOT NULL," +
                                                            "${MMDContract.columnas.DOCTOR_CITA} TEXT," +
                                                            "${MMDContract.columnas.ESPECIALIDAD_DOCTOR_CITA} TEXT," +
                                                            "${MMDContract.columnas.NOTA_CITA} TEXT," +
                                                            "${MMDContract.columnas.FECHA_HORA_CITA} TEXT," +
                                                            "${MMDContract.columnas.UBICACION_CITA} TEXT," +
                                                            "${MMDContract.columnas.TIPO_RECORDATORIO_CITA} INTEGER," +
                                                            "${MMDContract.columnas.COLOR_DISTINTIVO_CITA} TEXT NOT NULL," +
                                                            "${MMDContract.columnas.LATITUD_CITA} TEXT," +
                                                            "${MMDContract.columnas.LONGITUD_CITA} TEXT," +
                                                            "${MMDContract.columnas.USUARIO_CITA_ID} INTEGER," +
                                                            "FOREIGN KEY(${MMDContract.columnas.USUARIO_CITA_ID}) REFERENCES ${MMDContract.columnas.TABLA_USUARIO} (${BaseColumns._ID}) " +
                                                            "ON DELETE CASCADE)"

private const val SQL_DELETE_APPOINTMENTS_TABLE : String = "DROP TABLE IF EXISTS ${MMDContract.columnas.TABLA_CITA}"



class mmrbd(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USERS_TABLE)
        db.execSQL(SQL_CREATE_ESTABLISHMENT_TABLE)
        db.execSQL(SQL_CREATE_MEDICS_TABLE)
        db.execSQL(SQL_CREATE_CONTACT_CARD_TABLE)
        db.execSQL(SQL_CREATE_MEDICINE_TABLE)
        db.execSQL(SQL_CREATE_APPOINTMENTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_USER_TABLE)
        db.execSQL(SQL_DELETE_ESTABLISHMENT_TABLE)
        db.execSQL(SQL_DELETE_MEDICS_TABLE)
        db.execSQL(SQL_DELETE_CONTACT_CARD_TABLE)
        db.execSQL(SQL_DELETE_MEDICINE_TABLE)
        db.execSQL(SQL_DELETE_APPOINTMENTS_TABLE)

        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }


    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MMR.db"
    }
}
