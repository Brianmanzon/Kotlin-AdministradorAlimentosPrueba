package edu.labneo.saludable_tpfinal.api

import edu.labneo.saludable_tpfinal.model.Bebida
import retrofit2.Call
import retrofit2.http.GET
//Interaz del a API
interface ApiBebidas {
    @GET("api/json/v1/1/random.php")
    fun getBebida(): Call<Bebida>
}