package edu.labneo.saludable_tpfinal.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import edu.labneo.censo2021.adapters.ResourceStore
import edu.labneo.saludable_tpfinal.R
import edu.labneo.saludable_tpfinal.viewmodel.ComidaViewModel
import kotlinx.android.synthetic.main.activity_main.*


public class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        renderViewPager()
        renderTabLayer()
    }

    private fun renderViewPager(){

        viewpager.adapter = object: FragmentStateAdapter(this){

            override fun createFragment(position: Int): Fragment {
                return ResourceStore.pagerFragments[position]
            }
            override fun getItemCount(): Int {
                return ResourceStore.tabList.size
            }
        }
    }
    private fun renderTabLayer() {
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = getString(ResourceStore.tabList[position])
        }.attach()

    }
    //Menu personalizado con dos botones, uno para deslogear y otro para sincronizar a Firebase. La sincronización se hace si
    //Hay conexión, y si hay al menos un registro en la DB de SQLite.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_desloguear ->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.nav_sincro -> {
                val comidaVM: ComidaViewModel = ViewModelProvider(this).get(ComidaViewModel::class.java)
                if (comidaVM.isOnline(this)) {
                    if(comidaVM.hayDatos(this)>0){
                        comidaVM.sincronizarFirebase(this)
                        Toast.makeText(this, "Datos sincronizados correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "La base de datos local está vacía!", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(this, "Debe estar conectado a internet para sincronizar!", Toast.LENGTH_SHORT).show()
                }

            }
        }


        return super.onOptionsItemSelected(item)
    }
}
