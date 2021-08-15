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
import edu.labneo.saludable_tpfinal.databinding.FragmentCenaBinding
import edu.labneo.saludable_tpfinal.model.Comida
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
//Fragmento de la tab cena
class CenaFragment: Fragment() {
    private lateinit var cenaBinding: FragmentCenaBinding
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cenaBinding = FragmentCenaBinding.inflate(inflater, container, false)

        return cenaBinding.root


    }

    companion object {
        fun create(): CenaFragment {
            return CenaFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var nombre = requireActivity().intent.getStringExtra("nombre")
        var apellido = requireActivity().intent.getStringExtra("apellido")
        cenaBinding.cTNombre.setText(nombre+" "+apellido)
        cenaBinding.cTTipo.setText("Cena")
        cenaBinding.dTPostre.visibility = View.INVISIBLE
        cenaBinding.dTDeseo.visibility = View.INVISIBLE
        cenaBinding.outlinedTextField5.visibility = View.INVISIBLE
        cenaBinding.outlinedTextField6.visibility = View.INVISIBLE
        cenaBinding.radioButton2.setChecked(true)
        cenaBinding.radioButton4.setChecked(true)
        cenaBinding.radioButton6.setChecked(true)


        cenaBinding.iRgpostre.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioButton){
                cenaBinding.dTPostre.visibility = View.VISIBLE
                cenaBinding.outlinedTextField5.visibility = View.VISIBLE
            }
            if(checkedId == R.id.radioButton2){
                cenaBinding.dTPostre.visibility = View.INVISIBLE
                cenaBinding.outlinedTextField5.visibility = View.INVISIBLE
                cenaBinding.dTPostre.setText("")
            }
        }
        cenaBinding.iRgdeseo.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioButton3){
                cenaBinding.dTDeseo.visibility = View.VISIBLE
                cenaBinding.outlinedTextField6.visibility = View.VISIBLE
            }
            if(checkedId == R.id.radioButton4){
                cenaBinding.dTDeseo.visibility = View.INVISIBLE
                cenaBinding.outlinedTextField6.visibility = View.INVISIBLE
                cenaBinding.dTDeseo.setText("")
            }
        }

        cenaBinding.rBGuardar.setOnClickListener{
            var comidaVM: ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
            val conectividad = comidaVM.isOnline(view.context)
            val deseoSelected : Int = cenaBinding.iRgdeseo.checkedRadioButtonId
            val rb_selected: RadioButton = view.findViewById(deseoSelected)
            val deseo : String = rb_selected.text.toString()
            val hambreSelected : Int = cenaBinding.iRghambre.checkedRadioButtonId
            val rb_selected2: RadioButton = view.findViewById(hambreSelected)
            val hambre : String = rb_selected2.text.toString()
            val postreSelected: Int = cenaBinding.iRgpostre.checkedRadioButtonId
            val rb_selected3: RadioButton = view.findViewById(postreSelected)
            val postre: String = rb_selected3.text.toString()
            val currentDateTime = LocalDateTime.now()
            var hora = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var bebidaDialog = BebidaDialogFragment()



            if (conectividad){
                var nombre = getActivity()?.intent?.getStringExtra("nombre")
                var apellido = getActivity()?.intent?.getStringExtra("apellido")
                if(cenaBinding.dTPrincipal.text!!.toString().isNotEmpty() && cenaBinding.dTBebida.text!!.isNotEmpty()){
                    comidaVM.pushFirebase(
                        Comida(nombre!!,
                            apellido!!,
                            cenaBinding.cTTipo.text.toString(),
                            cenaBinding.dTPrincipal.text.toString(),
                            cenaBinding.dTSecundaria.text.toString(),
                            cenaBinding.dTBebida.text.toString(),
                            postre,
                            cenaBinding.dTPostre.text.toString(),
                            deseo,
                            cenaBinding.dTDeseo.text.toString(),
                            hambre,
                            hora), view.context)
                    Toast.makeText(it.context, "Comida guardada en la nube!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    cenaBinding.dTPrincipal.setText("")
                    cenaBinding.dTSecundaria.setText("")
                    cenaBinding.dTBebida.setText("")
                    cenaBinding.dTDeseo.setText("")
                    cenaBinding.dTPostre.setText("")
                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()
                }
            } else{
                if(cenaBinding.dTPrincipal.text!!.toString().isNotEmpty() && cenaBinding.dTBebida.text!!.isNotEmpty()){
                    var nombre = requireActivity().intent.getStringExtra("nombre")
                    var apellido = requireActivity().intent.getStringExtra("apellido")
                    comidaVM.saveComida(nombre!!,
                        apellido!!,
                        cenaBinding.cTTipo.text.toString(),
                        cenaBinding.dTPrincipal.text.toString(),
                        cenaBinding.dTSecundaria.text.toString(),
                        cenaBinding.dTBebida.text.toString(),
                        postre,
                        cenaBinding.dTPostre.text.toString(),
                        deseo,
                        cenaBinding.dTDeseo.text.toString(),
                        hambre,
                        hora,
                        view.context)
                    Toast.makeText(it.context, "Comida guardada localmente!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    cenaBinding.dTPrincipal.setText("")
                    cenaBinding.dTSecundaria.setText("")
                    cenaBinding.dTBebida.setText("")
                    cenaBinding.dTDeseo.setText("")
                    cenaBinding.dTPostre.setText("")

                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}