package edu.labneo.saludable_tpfinal.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.system.Os.uname
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import edu.labneo.saludable_tpfinal.model.Comida
import edu.labneo.saludable_tpfinal.model.Usuario

//Como se puede ver en las funciones debajo. Están hechas las funciones para implementar un sistema de login y guardado de datos
//Utilizando completamente Firebase. Pero considerando la consigna que decía que el programa debía ser capaz de funcionar offline
//Consideré eso como que el mismo debía ser completamente usable offline en todas las circumstancias, al igual que el hecho de utilizar
//Todo lo visto en el Lab, entre ello SQLite.
//En un caso real, personalmente, creo que sería mejor utilizar simplemente Firebase, ya que trabaja con persistencia de datos
//Y hasta sería capaz de funcionar mediante caché hasta para hacer el login, exceptuando algunos casos particulares
//(Que el usuario esté intentando utilizar un usuario que no registró en ese dispositivo por primera vez sin conexión, por ejemplo)

//Por esas razones, actualmente la aplicación funciona haciendo login mediante SQLite, guarda en Firebase si hay conexión, si no la hay
//Guarda en SQLite, y permite al usuario sincronizar los datos locales a la nube mediante un botón en la toolbar.
class DbHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        //Creación de las Tablas y base.
        private val DATABASE_NAME = "saludableDB.db"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "personas"
        val COLUMN_ID = "id"
        val COLUMN_NOMBRE = "nombre"
        val COLUMN_APELLIDO = "apellido"
        val COLUMN_TIPODOC = "tipo_doc"
        val COLUMN_DNI = "documento"
        val COLUMN_FECHA_NAC = "fecha_nac"
        val COLUMN_SEXO = "sexo"
        val COLUMN_LOCALIDAD = "localidad"
        val COLUMN_USUARIO = "usuario"
        val COLUMN_PASS = "pass"
        val COLUMN_TRATAMIENTO = "tratamiento"

        val TABLE_NAME2 = "comidas"
        val COLUMN_ID2 = "id"
        val COLUMN_TIPO = "tipo_comida"
        val COLUMN_PRINCIPAL = "comida_principal"
        val COLUMN_SECUNDARIA = "comida_secundaria"
        val COLUMN_BEBIDA = "bebida"
        val COLUMN_POSTRE = "postre"
        val COLUMN_POSTRETEXT = "postre_texto"
        val COLUMN_TENTACION = "tentacion"
        val COLUMN_TENTACIONTEXT = "tentacion_texto"
        val COLUMN_HAMBRE = "quedo_hambre"
        val COLUMN_HORA = "dia_hora"
    }


    override fun onCreate(db: SQLiteDatabase?) {

        var createTable =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY," +
                    "$COLUMN_NOMBRE TEXT," +
                    "$COLUMN_APELLIDO TEXT," +
                    "$COLUMN_TIPODOC TEXT," +
                    "$COLUMN_DNI TEXT," +
                    "$COLUMN_FECHA_NAC TEXT," +
                    "$COLUMN_SEXO TEXT," +
                    "$COLUMN_LOCALIDAD TEXT," +
                    "$COLUMN_USUARIO TEXT," +
                    "$COLUMN_PASS TEXT," +
                    "$COLUMN_TRATAMIENTO STRING)"

        var createTable2 =
            "CREATE TABLE $TABLE_NAME2 (" +
                    "$COLUMN_ID2 INTEGER PRIMARY KEY," +
                    "$COLUMN_NOMBRE TEXT," +
                    "$COLUMN_APELLIDO TEXT," +
                    "$COLUMN_TIPO TEXT," +
                    "$COLUMN_PRINCIPAL TEXT," +
                    "$COLUMN_SECUNDARIA TEXT," +
                    "$COLUMN_BEBIDA TEXT," +
                    "$COLUMN_POSTRE TEXT," +
                    "$COLUMN_POSTRETEXT TEXT," +
                    "$COLUMN_TENTACION TEXT," +
                    "$COLUMN_TENTACIONTEXT TEXT," +
                    "$COLUMN_HAMBRE TEXT," +
                    "$COLUMN_HORA TEXT)"

        db?.execSQL(createTable)
        db?.execSQL(createTable2)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
        onCreate(db)
    }

    //Funcion para guardar usuario en SQLite
    fun saveUsuario(usuario: Usuario): Boolean {
        try {
            val db = this.writableDatabase

            val values = ContentValues()

            values.put(COLUMN_NOMBRE, usuario.nombre.capitalize())
            values.put(COLUMN_APELLIDO, usuario.apellido.capitalize())
            values.put(COLUMN_TIPODOC, usuario.tipo_doc)
            values.put(COLUMN_DNI, usuario.doc)
            values.put(COLUMN_FECHA_NAC, usuario.fecha_nac)
            values.put(COLUMN_SEXO, usuario.sexo)
            values.put(COLUMN_LOCALIDAD, usuario.localidad)
            values.put(COLUMN_USUARIO, usuario.usuario)
            values.put(COLUMN_PASS, usuario.pass)
            values.put(COLUMN_TRATAMIENTO, usuario.tratamiento)

            db.insert(TABLE_NAME, null, values)

            return true

        } catch (e: Exception) {
            Log.e("ERROR DATABASE", e.message.toString())
            return false
        }
    }

    //funciones usada para el testing

    fun clearDbAndRecreate() {
        clearDb()
        onCreate(writableDatabase)
    }

    fun clearDb() {
        writableDatabase?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        writableDatabase?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
    }

    fun getAllUsuarios(): ArrayList<Usuario> {
        val db = this.readableDatabase
        val listaUsuarios: ArrayList<Usuario> = ArrayList<Usuario>()
        val query = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))
                val tipodoc = cursor.getString(cursor.getColumnIndex(COLUMN_TIPODOC))
                val doc = cursor.getString(cursor.getColumnIndex(COLUMN_DNI))
                val fechaNac = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_NAC))
                val sexo = cursor.getString(cursor.getColumnIndex(COLUMN_SEXO))
                val localidad = cursor.getString(cursor.getColumnIndex(COLUMN_LOCALIDAD))
                val usuario = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO))
                val pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASS))
                val tratamiento = cursor.getString(cursor.getColumnIndex(COLUMN_TRATAMIENTO))
                listaUsuarios.add(
                    Usuario(
                        id,
                        nombre,
                        apellido,
                        tipodoc,
                        doc,
                        fechaNac,
                        sexo,
                        localidad,
                        usuario,
                        pass,
                        tratamiento,
                    )
                )
            } while (cursor.moveToNext())
        }
        return listaUsuarios
    }
    //Funcion para gurdar las comidas en SQLite
    fun saveComida(comida: Comida): Boolean {
        try {
            val db = this.writableDatabase

            val values = ContentValues()

            values.put(COLUMN_NOMBRE, comida.nombre.capitalize())
            values.put(COLUMN_APELLIDO, comida.apellido.capitalize())
            values.put(COLUMN_TIPO, comida.tipo)
            values.put(COLUMN_PRINCIPAL, comida.principal)
            values.put(COLUMN_SECUNDARIA, comida.secundaria)
            values.put(COLUMN_BEBIDA, comida.bebida)
            values.put(COLUMN_POSTRE, comida.postre)
            values.put(COLUMN_POSTRETEXT, comida.postreText)
            values.put(COLUMN_TENTACION, comida.tentacion)
            values.put(COLUMN_TENTACIONTEXT, comida.tentacionText)
            values.put(COLUMN_HAMBRE, comida.hambre)
            values.put(COLUMN_HORA, comida.hora)



            db.insert(TABLE_NAME2, null, values)

            return true

        } catch (e: Exception) {
            Log.e("ERROR DATABASE", e.message.toString())
            return false
        }
    }
    //Función borrar los datos de SQLite luego de sincronizar a Firebase
    fun sincronizar() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME2, null, null)
    }
    //Función que trae la lista de comidas, usada para sincronizar.
    fun getAllComidas(): ArrayList<Comida> {
        val db = this.readableDatabase
        val listaComidas: ArrayList<Comida> = ArrayList<Comida>()
        val query = "SELECT * FROM $TABLE_NAME2"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))
                val tipo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val principal = cursor.getString(cursor.getColumnIndex(COLUMN_PRINCIPAL))
                val secundaria = cursor.getString(cursor.getColumnIndex(COLUMN_SECUNDARIA))
                val bebida = cursor.getString(cursor.getColumnIndex(COLUMN_BEBIDA))
                val postre = cursor.getString(cursor.getColumnIndex(COLUMN_POSTRE))
                val postreText = cursor.getString(cursor.getColumnIndex(COLUMN_POSTRETEXT))
                val tentacion = cursor.getString(cursor.getColumnIndex(COLUMN_TENTACION))
                val tentacionText = cursor.getString(cursor.getColumnIndex(COLUMN_TENTACIONTEXT))
                val hambre = cursor.getString(cursor.getColumnIndex(COLUMN_HAMBRE))
                val hora = cursor.getString(cursor.getColumnIndex(COLUMN_HORA))
                listaComidas.add(
                    Comida(
                        nombre,
                        apellido,
                        tipo,
                        principal,
                        secundaria,
                        bebida,
                        postre,
                        postreText,
                        tentacion,
                        tentacionText,
                        hambre,
                        hora
                    )
                )
            } while (cursor.moveToNext())
        }
        return listaComidas
    }
    //Función para hacer el login mediante SQLite
    fun ingresar(usuario: String, pass: String): ArrayList<String> {

        val db = this.writableDatabase
        val datosPersona: ArrayList<String> = ArrayList<String>()
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USUARIO=? AND $COLUMN_PASS=?"


        val cursor: Cursor = db.rawQuery(query, arrayOf<String>(usuario, pass))
        if (cursor.moveToFirst()) {

            do {
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))


                datosPersona.add(nombre)
                datosPersona.add(apellido)
            } while (cursor.moveToNext())
        }
        return datosPersona
    }

    //Función que guardar las comidas en Firebase cuando hay conexión a internet.
    fun pushFirebase(comida: Comida) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.getReference()

        databaseReference.child("Comidas").push().setValue(comida)
    }
    //Función que copia todos los datos de SQLite a Firebase, y luego borra los datos copiados en SQLite.
    fun sincronizarFirebase(context: Context) {
        val db: DbHelper = DbHelper(context, null)
        val dataList = db.getAllComidas()
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.getReference()

        for (comida in dataList) {
            databaseReference.child("Comidas").push().setValue(comida)
        }
        db.sincronizar()

    }
    //Función para registrar el usuario mediante Firebase.
    fun registrarUsuarioFirebase(email: String, password: String) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
    }
    //Función para logear mediante Firebase.
    fun loginFirebase(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)

    }
    //Función que verifica que haya un usuario logeado en Firebase.
    fun verificarUsuario():Boolean{
        val user = Firebase.auth.currentUser
        if (user != null) {
            return true
        } else {
            return false
        }
    }
    //Función se fija que el usuario no esté intentando registrar un DNI o usuario ya existente en SQLite
    fun validarRegistro(dni: String, usuario: String): Boolean {
        val db = this.readableDatabase
        val datosPersona : ArrayList<String> = ArrayList<String>()
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USUARIO=? OR $COLUMN_DNI=?"
        val cursor: Cursor = db.rawQuery(query, arrayOf<String>(usuario, dni))

        if (cursor.moveToFirst()) {

            do {
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))


                datosPersona.add(nombre)
                datosPersona.add(apellido)
            } while (cursor.moveToNext())
        }
        if (datosPersona.size>0){
            return false
        }else{
            return true
        }


    }
}