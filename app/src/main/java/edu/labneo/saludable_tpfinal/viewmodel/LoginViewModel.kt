package edu.labneo.saludable_tpfinal.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import edu.labneo.saludable_tpfinal.dao.DbHelper
import edu.labneo.saludable_tpfinal.model.Usuario

class LoginViewModel: ViewModel() {
    //Función para registrar un usuario en SQLite
    fun saveUsuario(nombre: String,
                    apellido: String,
                    tipo_doc: String,
                    doc: String,
                    fecha_nac: String,
                    sexo: String,
                    localidad: String,
                    usuario: String,
                    pass:String,
                    tratamiento:String,
                    context:Context) {


        val db: DbHelper = DbHelper(context,null)

        db.saveUsuario(Usuario(0, nombre,apellido,tipo_doc,doc,fecha_nac,sexo,localidad,usuario,pass,tratamiento))
    }

    //función para logear en SQLite
    fun verificar(usuario: String, pass: String, context: Context):ArrayList<String>{

        val db: DbHelper = DbHelper(context, null)
        val listaDatos = db.ingresar(usuario, pass)
        return listaDatos
    }

    //Función para registrar un usuario en Firebase
    fun RegistrarUsuarioFirebase(email: String, pass: String, context:Context){
        val db: DbHelper = DbHelper(context, null)
        db.registrarUsuarioFirebase(email, pass)
    }
    //Función para logear en Firebase
    fun LoginFirebase(email:String, pass:String, context: Context){
        val db: DbHelper = DbHelper(context, null)
        db.loginFirebase(email,pass)
    }
    //Función para verificar que el usuario esté logeado en Firebase
    fun verificarUsuario(context: Context): Boolean{
        val db: DbHelper = DbHelper(context, null)
        return db.verificarUsuario()
    }
    //Función para verificar que el usuario que se está registrando no exista (tanto DNI como nombre de usuario)
    fun usuarioRepetido (dni: String, usuario: String, context:Context): Boolean{
        val db: DbHelper = DbHelper(context, null)
        return db.validarRegistro(dni, usuario)
    }

}