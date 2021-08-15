package edu.labneo.saludable_tpfinal

import edu.labneo.saludable_tpfinal.dao.DbHelper
import edu.labneo.saludable_tpfinal.model.Comida
import edu.labneo.saludable_tpfinal.model.Usuario
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class saveUsuarioTest{
    lateinit var db: DbHelper

    @Before
    fun setup(){
        db = DbHelper(RuntimeEnvironment.application, null)
        db.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun TestDbSaveUsuario(){

        //GIVEN
        val usuario: Usuario = Usuario(1,"Brian","Manzon","DNI","38346604","13/5/1994","Masculino", "Tortuguitas","brian","andariel94","Anorexia")
        var listaUsuarios: ArrayList<Usuario> = ArrayList<Usuario>()
        listaUsuarios.add(usuario)

        //WHEN
        db.saveUsuario(usuario)

        //THEN
        assertEquals(db.getAllUsuarios(),listaUsuarios)

    }
    @After
    fun tearDown() {
        db.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)

class SaveComidaTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbSaveComida() {

        // Given
        val comida: Comida = Comida("Brian", "Manzon", "Desayuno","tostadas","mermelada","café","","","Si","Medialuna","No","2021-07-30 08:43:21")
        var listaComidas: ArrayList<Comida> = ArrayList<Comida>()
        listaComidas.add(comida)



        // When
        dbHelper.saveComida(comida)

        // Then
        assertEquals(dbHelper.getAllComidas(), listaComidas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)

class GetAllUsuariosTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbGetAllPersonas() {

        // Given
        val persona: Usuario = Usuario(1, "Brian", "Manzon","DNI","38346604","13/5/1994","Masculino","Tortuguitas","brian","1234","Obesidad")
        val persona2: Usuario = Usuario(2, "Alexis", "Manzon","CI","12312312","20/8/2015","Masculino","Manuel Alberti","alexis","1234","Anorexia")
        val persona3: Usuario = Usuario(3, "Luis", "Manzon","LC","42351235","25/11/1983","Masculino","Villa Adelina","luis","1234","Bulimia")
        val persona4: Usuario = Usuario(4, "Estela", "Masotta","DNI","5234523","1/2/1920","Femenino","Boulogne","estela","1234","Obesidad")
        val persona5: Usuario = Usuario(5, "Nadia", "Alvarez","DNI","544325445","10/10/1990","Masculino","Retiro","nadia","1234","Obesidad")
        var listaPersonas: ArrayList<Usuario> = ArrayList<Usuario>()
        listaPersonas.add(persona)
        listaPersonas.add(persona2)
        listaPersonas.add(persona3)
        listaPersonas.add(persona4)
        listaPersonas.add(persona5)
        dbHelper.saveUsuario(persona)
        dbHelper.saveUsuario(persona2)
        dbHelper.saveUsuario(persona3)
        dbHelper.saveUsuario(persona4)
        dbHelper.saveUsuario(persona5)


        // When

        val getAllUsuarios = dbHelper.getAllUsuarios()

        // Then
        assertEquals(getAllUsuarios, listaPersonas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)

class GetAllComidasTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbGetAllPersonas() {

        // Given
        val comida: Comida = Comida("Brian", "Manzon", "Desayuno","Medialuna","nada","te","","","No","","No","2021-07-30 08:43:21")
        val comida2: Comida = Comida("Brian", "Manzon", "Cena","Fideos con salsa","nada","agua","Si","flan","No","","No","2021-07-30 20:43:21")
        val comida3: Comida = Comida("Brian", "Manzon", "Almuerzo","Hamburguesa","Ensalada","coca","No","","Si","Helado","Si","2021-07-30 13:43:21")
        val comida4: Comida = Comida("Brian", "Manzon", "Merienda","Pan","Galletitas","cafe","","","No","","No","2021-07-30 17:43:21")
        val comida5: Comida = Comida("Brian", "Manzon", "Desayuno","Medialuna","Tostadas","chocolatada","","","Si","Galletitas","No","2021-07-30 07:43:21")
        var listaComidas: ArrayList<Comida> = ArrayList<Comida>()
        listaComidas.add(comida)
        listaComidas.add(comida2)
        listaComidas.add(comida3)
        listaComidas.add(comida4)
        listaComidas.add(comida5)
        dbHelper.saveComida(comida)
        dbHelper.saveComida(comida2)
        dbHelper.saveComida(comida3)
        dbHelper.saveComida(comida4)
        dbHelper.saveComida(comida5)


        // When

        val getAllComidas = dbHelper.getAllComidas()

        // Then
        assertEquals(getAllComidas, listaComidas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)
class IngresarTest{
lateinit var dbHelper: DbHelper

@Before
fun setup() {
    dbHelper = DbHelper(RuntimeEnvironment.application, null)
    dbHelper.clearDbAndRecreate()
}

@Test
@Throws(Exception::class)
fun testDbGetAllPersonas() {

    // Given
    val usuario: Usuario = Usuario(0,"Brian", "Manzon", "DNI","38346604","13/5/1994","Masculino","Tortuguitas","brian","mercurius94","Anorexia")
    var datos: ArrayList<String> = ArrayList<String>()
    datos.add("Brian")
    datos.add("Manzon")
    dbHelper.saveUsuario(usuario)


    // When

    val ingresar = dbHelper.ingresar("brian","mercurius94")

    // Then
    assertEquals(ingresar, datos)
}

@After
fun tearDown() {
    dbHelper.clearDb()
}
}


