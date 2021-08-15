package edu.labneo.saludable_tpfinal.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.labneo.saludable_tpfinal.R
import edu.labneo.saludable_tpfinal.databinding.FragmentDesayunoBinding
import edu.labneo.saludable_tpfinal.model.Comida
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
//Fragmento de la tab desayuno
class DesayunoFragment: Fragment() {
    private lateinit var desayunoBinding: FragmentDesayunoBinding
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        desayunoBinding = FragmentDesayunoBinding.inflate(inflater, container, false)

        return desayunoBinding.root


    }

    companion object {
        fun create(): DesayunoFragment {
            return DesayunoFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var nombre = requireActivity().intent.getStringExtra("nombre")
        var apellido = requireActivity().intent.getStringExtra("apellido")
        desayunoBinding.dTNombre.setText(nombre+" "+apellido)
        desayunoBinding.dTTipo.setText("Desayuno")
        desayunoBinding.dTDeseo.visibility = INVISIBLE
        desayunoBinding.outlinedTextField6.visibility = INVISIBLE
        desayunoBinding.radioButton4.setChecked(true)
        desayunoBinding.radioButton6.setChecked(true)


        desayunoBinding.iRgdeseo.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioButton3){
                desayunoBinding.dTDeseo.visibility = VISIBLE
                desayunoBinding.outlinedTextField6.visibility = VISIBLE
            }
            if(checkedId == R.id.radioButton4){
                desayunoBinding.dTDeseo.visibility = INVISIBLE
                desayunoBinding.outlinedTextField6.visibility = INVISIBLE
                desayunoBinding.dTDeseo.setText("")
            }
        }



        desayunoBinding.dBGuardar.setOnClickListener{
            var comidaVM: ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
            val conectividad = comidaVM.isOnline(view.context)
            val deseoSelected : Int = desayunoBinding.iRgdeseo.checkedRadioButtonId
            val rb_selected: RadioButton = view.findViewById(deseoSelected)
            val deseo : String = rb_selected.text.toString()
            val hambreSelected : Int = desayunoBinding.iRghambre.checkedRadioButtonId
            val rb_selected2: RadioButton = view.findViewById(hambreSelected)
            val hambre : String = rb_selected2.text.toString()
            val currentDateTime = LocalDateTime.now()
            var hora = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var bebidaDialog = BebidaDialogFragment()



            if (conectividad){
                var nombre = getActivity()?.intent?.getStringExtra("nombre")
                var apellido = getActivity()?.intent?.getStringExtra("apellido")
                if(desayunoBinding.dTPrincipal.text!!.toString().isNotEmpty() && desayunoBinding.dTBebida.text!!.isNotEmpty()){
                    comidaVM.pushFirebase(Comida(nombre!!,
                        apellido!!,
                        desayunoBinding.dTTipo.text.toString(),
                        desayunoBinding.dTPrincipal.text.toString(),
                        desayunoBinding.dTSecundaria.text.toString(),
                        desayunoBinding.dTBebida.text.toString(),
                        "",
                        "",
                        deseo,
                        desayunoBinding.dTDeseo.text.toString(),
                        hambre,
                        hora), view.context)
                    Toast.makeText(it.context, "Comida guardada en la nube!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    desayunoBinding.dTPrincipal.setText("")
                    desayunoBinding.dTSecundaria.setText("")
                    desayunoBinding.dTBebida.setText("")
                    desayunoBinding.dTDeseo.setText("")
                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()
                }

            } else{
                if(desayunoBinding.dTPrincipal.text!!.toString().isNotEmpty() && desayunoBinding.dTBebida.text!!.isNotEmpty()){
                    var nombre = requireActivity().intent.getStringExtra("nombre")
                    var apellido = requireActivity().intent.getStringExtra("apellido")
                    comidaVM.saveComida(nombre!!,
                        apellido!!,
                        desayunoBinding.dTTipo.text.toString(),
                        desayunoBinding.dTPrincipal.text.toString(),
                        desayunoBinding.dTSecundaria.text.toString(),
                        desayunoBinding.dTBebida.text.toString(),
                        "",
                        "",
                        deseo,
                        desayunoBinding.dTDeseo.text.toString(),
                        hambre,
                        hora,
                        view.context)

                    Toast.makeText(it.context, "Comida guardada localmente!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    desayunoBinding.dTPrincipal.setText("")
                    desayunoBinding.dTSecundaria.setText("")
                    desayunoBinding.dTBebida.setText("")
                    desayunoBinding.dTDeseo.setText("")

                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}
