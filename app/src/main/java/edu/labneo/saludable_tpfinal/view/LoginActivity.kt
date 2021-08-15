package edu.labneo.saludable_tpfinal.view

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import edu.labneo.saludable_tpfinal.databinding.ActivityLoginBinding
import edu.labneo.saludable_tpfinal.fragments.DatePickerFragment
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import edu.labneo.saludable_tpfinal.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loginVM: LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val documentos = listOf("DNI", "CI", "LC","LE","OTRO")
        val sexo= listOf("Masculino","Femenino","Otro")
        val tratamientos= listOf("Anorexia","Bulimia","Obesidad")
        var docSeleccionado = ""
        var sexoSeleccionado = ""
        var tratSeleccionado = ""


        binding.rSSex.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, sexo)
        binding.rSTratamiento.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, tratamientos)
        binding.rSDoc.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, documentos)

        binding.rSDoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                docSeleccionado= documentos[position]
            }
        }

        binding.rSSex.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sexoSeleccionado= sexo[position]
            }
        }


        binding.rSTratamiento.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tratSeleccionado= tratamientos[position]
            }
        }
            invisible()


        binding.rTNac.setOnClickListener {
            fun onDateSelected(day: Int, month: Int, year: Int) {
                binding.rTNac.setText("$day/$month/$year")}
            fun showDatePickerDialog() {
                val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
                datePicker.show(supportFragmentManager, "datePicker")
            }
            showDatePickerDialog()
        }

        binding.iBRegistrarse.setOnClickListener{
            if (binding.rTNombre.visibility == INVISIBLE) {
                visible()

            } else{
                invisible()
            }




        }


        binding.rBRegistrar.setOnClickListener{

           if(binding.rTNombre.text!!.isNotEmpty() && binding.rTApellido.text!!.isNotEmpty() && docSeleccionado.isNotEmpty() && binding.rTDoc.text!!.isNotEmpty() && binding.rTNac.text!!.isNotEmpty() && sexoSeleccionado.isNotEmpty() && tratSeleccionado.isNotEmpty() && binding.rTLocalidad.text!!.isNotEmpty() && binding.rTUsuario.text!!.isNotEmpty() && binding.rTContraseA.text!!.isNotEmpty()){
               if(loginVM.usuarioRepetido(binding.rTDoc.text.toString(),binding.rTUsuario.text.toString(),this)){
                   loginVM.saveUsuario(binding.rTNombre.text.toString(),
                       binding.rTApellido.text.toString(),
                       docSeleccionado,
                       binding.rTDoc.text.toString(),
                       binding.rTNac.text.toString(),
                       sexoSeleccionado,
                       binding.rTLocalidad.text.toString(),
                       binding.rTUsuario.text.toString(),
                       binding.rTContraseA.text.toString(),
                       tratSeleccionado,
                       this)


                       binding.rTNombre.setText("")
                       binding.rTApellido.setText("")
                       binding.rTContraseA.setText("")
                       binding.rTDoc.setText("")
                       binding.rTLocalidad.setText("")
                       binding.rTNac.setText("")
                       binding.rTUsuario.setText("")
                       invisible()

                       Toast.makeText(applicationContext,"Persona registrada!", Toast.LENGTH_SHORT).show()
                       }else{
                       Toast.makeText(applicationContext,"Está ingresando un usuario o DNI ya existente, ingrese otro!", Toast.LENGTH_SHORT).show()
                       }
           }else{
               Toast.makeText(applicationContext,"Debe completar todos los campos!", Toast.LENGTH_SHORT).show()
           }


        }


        binding.iBIngresar.setOnClickListener{
         val listaDatos = loginVM.verificar(binding.iTUsuario.text.toString(),binding.iTPass.text.toString(), this)
         if (listaDatos.size>0){
              val intent = Intent(this, MainActivity::class.java)
              intent.putExtra("nombre", listaDatos[0])
              intent.putExtra("apellido", listaDatos[1])
              startActivity(intent)
              finish()
          } else{
              Toast.makeText(applicationContext,"Usuario/contraseña incorrecta!", Toast.LENGTH_SHORT).show()
          }

        }

        basicAlert()




    }
    fun invisible(){

        binding.rTNombre.visibility = INVISIBLE
        binding.rTApellido.visibility = INVISIBLE
        binding.rTContraseA.visibility = INVISIBLE
        binding.rTDoc.visibility = INVISIBLE
        binding.rTLocalidad.visibility = INVISIBLE
        binding.rTNac.visibility = INVISIBLE
        binding.rTUsuario.visibility = INVISIBLE
        binding.rSTratamiento.visibility = INVISIBLE
        binding.rSDoc.visibility = INVISIBLE
        binding.rSSex.visibility = INVISIBLE
        binding.rBRegistrar.visibility = INVISIBLE
        binding.outlinedTextField3.visibility = INVISIBLE
        binding.outlinedTextField4.visibility = INVISIBLE
        binding.outlinedTextField.visibility = INVISIBLE
        binding.outlinedTextField5.visibility = INVISIBLE
        binding.outlinedTextField6.visibility = INVISIBLE
        binding.outlinedTextField8.visibility = INVISIBLE
        binding.outlinedTextField9.visibility = INVISIBLE
    }
    fun visible(){
        binding.rTNombre.visibility = VISIBLE
        binding.rTApellido.visibility = VISIBLE
        binding.rTContraseA.visibility = VISIBLE
        binding.rTDoc.visibility = VISIBLE
        binding.rTLocalidad.visibility = VISIBLE
        binding.rTNac.visibility = VISIBLE
        binding.rTUsuario.visibility = VISIBLE
        binding.rSTratamiento.visibility = VISIBLE
        binding.rSDoc.visibility = VISIBLE
        binding.rSSex.visibility = VISIBLE
        binding.rBRegistrar.visibility = VISIBLE
        binding.outlinedTextField3.visibility = VISIBLE
        binding.outlinedTextField4.visibility = VISIBLE
        binding.outlinedTextField.visibility = VISIBLE
        binding.outlinedTextField5.visibility = VISIBLE
        binding.outlinedTextField6.visibility = VISIBLE
        binding.outlinedTextField8.visibility = VISIBLE
        binding.outlinedTextField9.visibility = VISIBLE
    }


    fun basicAlert(){
        val comidaVM : ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
        if (comidaVM.hayDatos(this)>0){
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle("Recordatorio")
                setMessage("Tiene uno o más registros de comida sin sincronizar, hágalo con el botón de la barra superior tras ingresar!")
                show()
            }
        }

    }
}