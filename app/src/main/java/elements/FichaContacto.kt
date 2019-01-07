package elements

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import model.MMDContract

class FichaContacto (var titulo : String? = null, var direccion : String = "", var telefono : String = "", var celular : String = "", var email : String = "",  var sitioweb : String = "", var accesoRapido : Boolean = false, var medico_ficha_id : Int = 0) : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt())

    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()

        contentValues.put(MMDContract.columnas.TITULO_FICHA_CONTACTO, titulo)
        contentValues.put(MMDContract.columnas.DIRECCION_FICHA_CONTACTO, direccion)
        contentValues.put(MMDContract.columnas.TELEFONO_FICHA_CONTACTO, telefono)
        contentValues.put(MMDContract.columnas.CELULAR_FICHA_CONTACTO, celular)
        contentValues.put(MMDContract.columnas.EMAIL_FICHA_CONTACTO, email)
        contentValues.put(MMDContract.columnas.WEB_FICHA_CONTACTO, sitioweb)
        if(accesoRapido){
            contentValues.put(MMDContract.columnas.ACCESSO_FICHA_CONTACTO, 1)
        }else{
            contentValues.put(MMDContract.columnas.ACCESSO_FICHA_CONTACTO, 0)
        }
        contentValues.put(MMDContract.columnas.DOCTOR_FICHA_CONTACTO_ID, medico_ficha_id)


        return contentValues
    }

    override fun toString() : String{
        return "$titulo $direccion $telefono $celular $email $sitioweb $$accesoRapido $medico_ficha_id"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(direccion)
        parcel.writeString(telefono)
        parcel.writeString(celular)
        parcel.writeString(email)
        parcel.writeString(sitioweb)
        parcel.writeByte(if (accesoRapido) 1 else 0)
        parcel.writeInt(medico_ficha_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FichaContacto> {
        override fun createFromParcel(parcel: Parcel): FichaContacto {
            return FichaContacto(parcel)
        }

        override fun newArray(size: Int): Array<FichaContacto?> {
            return arrayOfNulls(size)
        }
    }


}