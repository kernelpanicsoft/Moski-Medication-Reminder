package Elementos
import android.content.ContentValues
import model.MMDContract


class Medicamento (var nombreMedicamento: String? = null, var nombreGenerico: String? = null, var dosis: String? = null, var nota: String? = null, var tipo: String? = null, var color: String? = null, var fotografia: String? = null, var usuarioID : Int? = null) {

    fun toContentValues() : ContentValues{
        val contentValues = ContentValues()

        contentValues.put(MMDContract.columnas.NOMBRE_COMERCIAL_MEDICAMENTO, nombreMedicamento)
        contentValues.put(MMDContract.columnas.NOMBRE_GENERICO_MEDICAMENTO, nombreGenerico)
        contentValues.put(MMDContract.columnas.DOSIS_MEDICAMENTO, dosis)
        contentValues.put(MMDContract.columnas.COLOR_MEDICAMENTO, color)
        contentValues.put(MMDContract.columnas.TIPO_MEDICAMENTO, tipo)
        contentValues.put(MMDContract.columnas.FOTOGRAFIA_MEDICAMENTO, fotografia)
        contentValues.put(MMDContract.columnas.NOTA_MEDICAMENTO,nota)
        contentValues.put(MMDContract.columnas.USUARIO_MEDICAMENTO_ID, usuarioID)
        return contentValues

    }
}
