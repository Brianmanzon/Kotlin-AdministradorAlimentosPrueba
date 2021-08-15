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
import edu.labneo.saludable_tpfinal.databinding.FragmentMeriendaBinding
import edu.labneo.saludable_tpfinal.model.Comida
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
//Fragmento de la tab merienda
class MeriendaFragment: Fragment() {
    private lateinit var meriendaBinding: FragmentMeriendaBinding
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        meriendaBinding = FragmentMeriendaBinding.inflate(inflater, container, false)

        return meriendaBinding.root


    }

    companion object {
        fun create(): MeriendaFragment {
            return MeriendaFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var nombre = requireActivity().intent.getStringExtra("nombre")
        var apellido = requireActivity().intent.getStringExtra("apellido")
        meriendaBinding.mTNombre.setText(nombre+" "+apellido)
        meriendaBinding.mTTipo.setText("Merienda")
        meriendaBinding.dTDeseo.visibility = View.INVISIBLE
        meriendaBinding.outlinedTextField6.visibility = View.INVISIBLE
        meriendaBinding.radioButton4.setChecked(true)
        meriendaBinding.radioButton6.setChecked(true)


        meriendaBinding.iRgdeseo.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioButton3){
                meriendaBinding.dTDeseo.visibility = View.VISIBLE
                meriendaBinding.outlinedTextField6.visibility = View.VISIBLE
            }
            if(checkedId == R.id.radioButton4){
                meriendaBinding.dTDeseo.visibility = View.INVISIBLE
                meriendaBinding.outlinedTextField6.visibility = View.INVISIBLE
                meriendaBinding.dTDeseo.setText("")
            }
        }

        meriendaBinding.dBGuardar.setOnClickListener{
            var comidaVM: ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
            val conectividad = comidaVM.isOnline(view.context)
            val deseoSelected : Int = meriendaBinding.iRgdeseo.checkedRadioButtonId
            val rb_selected: RadioButton = view.findViewById(deseoSelected)
            val deseo : String = rb_selected.text.toString()
            val hambreSelected : Int = meriendaBinding.iRghambre.checkedRadioButtonId
            val rb_selected2: RadioButton = view.findViewById(hambreSelected)
            val hambre : String = rb_selected2.text.toString()
            val currentDateTime = LocalDateTime.now()
            var hora = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var bebidaDialog = BebidaDialogFragment()


            if (conectividad){
                var nombre = getActivity()?.intent?.getStringExtra("nombre")
                var apellido = getActivity()?.intent?.getStringExtra("apellido")
                if(meriendaBinding.dTPrincipal.text!!.toString().isNotEmpty() && meriendaBinding.dTBebida.text!!.isNotEmpty())
                {
                    comidaVM.pushFirebase(
                        Comida(nombre!!,
                            apellido!!,
                            meriendaBinding.mTTipo.text.toString(),
                            meriendaBinding.dTPrincipal.text.toString(),
                            meriendaBinding.dTSecundaria.text.toString(),
                            meriendaBinding.dTBebida.text.toString(),
                            "",
                            "",
                            deseo,
                            meriendaBinding.dTDeseo.text.toString(),
                            hambre,
                            hora), view.context)
                    Toast.makeText(it.context, "Comida guardada en la nube!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    meriendaBinding.dTPrincipal.setText("")
                    meriendaBinding.dTSecundaria.setText("")
                    meriendaBinding.dTBebida.setText("")
                    meriendaBinding.dTDeseo.setText("")
                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()
                }

            } else{
                if(meriendaBinding.dTPrincipal.text!!.toString().isNotEmpty() && meriendaBinding.dTBebida.text!!.isNotEmpty()){
                    var nombre = requireActivity().intent.getStringExtra("nombre")
                    var apellido = requireActivity().intent.getStringExtra("apellido")
                    comidaVM.saveComida(nombre!!,
                        apellido!!,
                        meriendaBinding.mTTipo.text.toString(),
                        meriendaBinding.dTPrincipal.text.toString(),
                        meriendaBinding.dTSecundaria.text.toString(),
                        meriendaBinding.dTBebida.text.toString(),
                        "",
                        "",
                        deseo,
                        meriendaBinding.dTDeseo.text.toString(),
                        hambre,
                        hora,
                        view.context)
                    Toast.makeText(it.context, "Comida guardada localmente!", Toast.LENGTH_SHORT).show()
                    bebidaDialog.show(childFragmentManager,"Gracias por usar la aplicación!")
                    meriendaBinding.dTPrincipal.setText("")
                    meriendaBinding.dTSecundaria.setText("")
                    meriendaBinding.dTBebida.setText("")
                    meriendaBinding.dTDeseo.setText("")
                }else{
                    Toast.makeText(it.context,"Debe completar al menos la comida principal y la bebida!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
