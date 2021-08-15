package edu.labneo.saludable_tpfinal.api.implementation

import edu.labneo.saludable_tpfinal.api.ApiBebidas
import edu.labneo.saludable_tpfinal.model.Bebida
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//Implementacion de la API de las bebidas.
class ApiBebidasImp {
    private  fun getRetrofit() : Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.thecocktaildb.com/")
            .build()
    }

    fun getBebida(): Call<Bebida> {


        return getRetrofit().create(ApiBebidas::class.java).getBebida()
    }

}