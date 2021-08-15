package edu.labneo.saludable_tpfinal.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.labneo.saludable_tpfinal.R
import edu.labneo.saludable_tpfinal.databinding.FragmentAlmuerzoBinding
import edu.labneo.saludable_tpfinal.model.Comida
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
//Fragmento del tab Almuerzo
class AlmuerzoFragment: Fragment() {
    private lateinit var almuerzoBinding: FragmentAlmuerzoBinding
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        almuerzoBinding = FragmentAlmuerzoBinding.inflate(inflater, container, false)

        return almuerzoBinding.root


    }

    companion object {
        fun create(): AlmuerzoFragment {
            return AlmuerzoFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var nombre = requireActivity().intent.getStringExtra("nombre")
        var apellido = requireActivity().intent.getStringExtra("apellido")
        almuerzoBinding.aTNombre.setText(nombre+" "+apellido)
        almuerzoBinding.aTTipo.setText("Almuerzo")
        almuerzoBinding.dTPostre.visibility = View.INVISIBLE
        almuerzoBinding.dTDeseo.visibility = View.INVISIBLE
        almuerzoBinding.outlinedTextField5.visibility = View.INVISIBLE
        almuerzoBinding.outlinedTextField6.visibility = View.INVISIBLE
        almuerzoBinding.radioButton2.setChecked(true)
        almuerzoBinding.radioButton4.setChecked(true)
        almuerzoBinding.radioButton6.setChecked(true)


        almuerzoBinding.iRgpostre.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioButton){
                almuerzoBinding.dTPostre.visibility = View.VISIBLE
                almuerzoBinding.outlinedTextField5.visibility = View.VISIBLE
            }
            if(checkedId == R.id.radioButton2){
                almuerzoBinding.dTPostre.visibility = View.INVISIBLE
                almuerzoBinding.outlinedTextField5.visibility = View.INVISIBLE
                almuerzoBinding.dTPostre.setText("")
            }
        }
        almuerzoBinding.iRgdeseo.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioButton3){
                almuerzoBinding.dTDeseo.visibility = View.VISIBLE
                almuerzoBinding.outlinedTextField6.visibility = View.VISIBLE
            }
            if(checkedId == R.id.radioButton4){
                almuerzoBinding.dTDeseo.visibility = View.INVISIBLE
                almuerzoBinding.outlinedTextField6.visibility = View.INVISIBLE
                almuerzoBinding.dTDeseo.setText("")
            }
        }

        almuerzoBinding.rBGuardar.setOnClickListener{
            var comidaVM: ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
            val conectividad = comidaVM.isOnline(view.context)
            val deseoSelected : Int = almuerzoBinding.iRgdeseo.checkedRadioButtonId
            val rb_selected: RadioButton = view.findViewById(deseoSelected)
            val deseo : String = rb_selected.text.toString()
            val hambreSelected : Int = almuerzoBinding.iRghambre.checkedRadioButtonId
            val rb_selected2: RadioButton = view.findViewById(hambreSelected)
            val hambre : String = rb_selected2.text.toString()
            val postreSelected: Int = almuerzoBinding.iRgpostre.checkedRadioButtonId
            val rb_selected3: RadioButton = view.findViewById(postreSelected)
            val postre: String = rb_selected3.text.toString()
            val currentDateTime = LocalDateTime.now()
            var hora = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var bebidaDialog = BebidaDialogFragment()



            if (conectividad){
                var nombre = getActivity()?.intent?.getStringExtra("nombre")
                var apellido = getActivity()?.intent?.getStringExtra("apellido")
                if(almuerzoBinding.dTPrincipal.text!!.toString().isNotEmpty() && almuerzoBinding.dTBebida.text!!.isNotEmpty()){
                    comidaVM.pushFirebase(
                        Comida(nombre!!,
                            apellido!!,
                            almuerzoBinding.aTTipo.text.toString(),
                            almuerzoBinding.dTPrincipal.text.toString(),
                            almuerzoBinding.dTSecundaria.text.toString(),
                            almuerzoBinding.dTBebida.text.toString(),
                            postre,
                            almuerzoBinding.dTPostre.text.toString(),
                            deseo,
                            almuerzoBinding.dTDeseo.text.toString(),
                            hambre,
                            hora), view.context)
                    Toast.makeText(it.context, "Comida guardada en la nube!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    almuerzoBinding.dTPrincipal.setText("")
                    almuerzoBinding.dTSecundaria.setText("")
                    almuerzoBinding.dTBebida.setText("")
                    almuerzoBinding.dTDeseo.setText("")
                    almuerzoBinding.dTPostre.setText("")
                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()

                }
            } else{
                if(almuerzoBinding.dTPrincipal.text!!.toString().isNotEmpty() && almuerzoBinding.dTBebida.text!!.isNotEmpty()){
                    var nombre = requireActivity().intent.getStringExtra("nombre")
                    var apellido = requireActivity().intent.getStringExtra("apellido")
                    comidaVM.saveComida(nombre!!,
                        apellido!!,
                        almuerzoBinding.aTTipo.text.toString(),
                        almuerzoBinding.dTPrincipal.text.toString(),
                        almuerzoBinding.dTSecundaria.text.toString(),
                        almuerzoBinding.dTBebida.text.toString(),
                        postre,
                        almuerzoBinding.dTPostre.text.toString(),
                        deseo,
                        almuerzoBinding.dTDeseo.text.toString(),
                        hambre,
                        hora,
                        view.context)
                    Toast.makeText(it.context, "Comida guardada localmente!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    almuerzoBinding.dTPrincipal.setText("")
                    almuerzoBinding.dTSecundaria.setText("")
                    almuerzoBinding.dTBebida.setText("")
                    almuerzoBinding.dTDeseo.setText("")
                    almuerzoBinding.dTPostre.setText("")

                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()

                }
            }
        }



    }
}