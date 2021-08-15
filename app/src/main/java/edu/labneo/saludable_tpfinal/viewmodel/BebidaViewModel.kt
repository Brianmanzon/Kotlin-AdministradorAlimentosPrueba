package edu.labneo.saludable_tpfinal.viewmodel

import androidx.lifecycle.ViewModel
import edu.labneo.saludable_tpfinal.api.implementation.ApiBebidasImp
import edu.labneo.saludable_tpfinal.model.Bebida
import retrofit2.Call
//VM de la API
class BebidaViewModel: ViewModel() {

    fun getBebida(): Call<Bebida> {
        val api: ApiBebidasImp = ApiBebidasImp()

        return api.getBebida()
    }
}