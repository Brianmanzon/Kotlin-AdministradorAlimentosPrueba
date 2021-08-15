package edu.labneo.saludable_tpfinal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import edu.labneo.saludable_tpfinal.R
import edu.labneo.saludable_tpfinal.model.Bebida
import edu.labneo.saludable_tpfinal.viewmodel.BebidaViewModel
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import retrofit2.Call
import retrofit2.Response
//Fragmento del diálogo que muestra la bebida tras registrar una comida.
class BebidaDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_bebida_dialog,container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val comidaVM: ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
        val info1: TextInputEditText = view.findViewById(R.id.b_t_info)
        val info2: TextInputEditText = view.findViewById(R.id.b_t_info2)
        val info3: TextInputEditText = view.findViewById(R.id.b_t_info3)
        val image: ImageView = view.findViewById(R.id.b_imagen)
        val regresar: MaterialButton = view.findViewById(R.id.b_bt_regresar)
        val bebidaVM: BebidaViewModel = ViewModelProvider(this).get(BebidaViewModel::class.java)
        if (comidaVM.isOnline(view.context)){
            bebidaVM.getBebida()
                .enqueue(object : retrofit2.Callback<Bebida> {
                    override fun onFailure(call: Call<Bebida>, t: Throwable){
                        Log.e("Error-al-traer-API", t.message.toString())
                    }
                    override fun onResponse(
                        call: Call<Bebida>,
                        response: Response<Bebida>
                    ){
                        if (response.body() != null){
                            val data = response.body()

                            info1.setText(data?.drinks?.get(0)?.idDrink)
                            info2.setText(data?.drinks?.get(0)?.strCategory)
                            info3.setText(data?.drinks?.get(0)?.strDrink)

                            Glide
                                .with(view.context)
                                .load(data?.drinks?.get(0)?.strDrinkThumb)
                                .centerCrop()
                                .into(image)
                            }
                        }
                    }
                )
            regresar.setOnClickListener{
                this.dismiss()
                }

        }else{
            info1.setText("Inexistente")
            info2.setText("No hay conexión")
            info3.setText("La bebida no llegó a salvo")

            Glide
                .with(view.context)
                .load(view.resources.getDrawable(R.drawable.vaso_vacio))
                .centerCrop()
                .into(image)
        }
        regresar.setOnClickListener{
            this.dismiss()
        }

    }
}