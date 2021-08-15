package edu.labneo.saludable_tpfinal.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import edu.labneo.saludable_tpfinal.dao.DbHelper
import edu.labneo.saludable_tpfinal.model.Comida
class ComidaViewModel: ViewModel() {
    //Función para guardar comida.
    fun saveComida(nombre: String,
                    apellido: String,
                    tipo: String,
                    principal: String,
                    secundaria: String,
                    bebida: String,
                    postre: String,
                    postreText: String,
                    tentacion:String,
                    tentacionText:String,
                    hambre: String,
                    hora: String,
                    context: Context
    ) {


        val db: DbHelper = DbHelper(context,null)

        db.saveComida(Comida(nombre,apellido,tipo,principal,secundaria,bebida,postre,postreText,tentacion,tentacionText,hambre,hora))
    }

    //Función que verifica que el teléfono tenga conexión a internet, devuelve True o False dependiendo de eso.
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }



    //Función para guardar comida en Firebase.
    fun pushFirebase(comida: Comida, context: Context){
        val db: DbHelper = DbHelper(context,null)
        db.pushFirebase(comida)
    }
    //Función para sincronizar los datos de la DB de SQLite a Firebase.
    fun sincronizarFirebase(context: Context){
        val db: DbHelper = DbHelper(context,null)
        db.sincronizarFirebase(context)
    }

    //Función que verifica si hay al menos un registro en la DB de SQLite
    fun hayDatos(context: Context): Int{
        val db: DbHelper = DbHelper(context, null)
        val listComidas = db.getAllComidas()
        return listComidas.size
    }
}