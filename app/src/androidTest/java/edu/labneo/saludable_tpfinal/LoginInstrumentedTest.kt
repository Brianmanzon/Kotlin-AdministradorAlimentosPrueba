package edu.labneo.saludable_tpfinal


import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import edu.labneo.saludable_tpfinal.view.LoginActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule



@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    //tiene que saltar el toast de login incorrecto
    @Test
    fun testCamposVacios(){
        Espresso.onView(ViewMatchers.withId(R.id.i_b_ingresar)).perform(ViewActions.click())
        Thread.sleep(200)

    }
    //tiene que saltar el toast de que hace falta llenar todos los campos
    @Test
    fun testCamposVaciosRegistro(){
        Espresso.onView(ViewMatchers.withId(R.id.i_b_registrarse)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.r_b_registrar)).perform(ViewActions.click())
        Thread.sleep(200)
    }
    //tiene que aparecer y desaparecer el formulario de registro
    @Test
    fun aparicionRegistro(){
        Espresso.onView(ViewMatchers.withId(R.id.i_b_registrarse)).perform(ViewActions.click())
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.i_b_registrarse)).perform(ViewActions.click())
        Thread.sleep(200)

    }
}




