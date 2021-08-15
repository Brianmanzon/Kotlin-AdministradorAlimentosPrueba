package edu.labneo.censo2021.adapters


import edu.labneo.saludable_tpfinal.R
import edu.labneo.saludable_tpfinal.fragments.AlmuerzoFragment
import edu.labneo.saludable_tpfinal.fragments.CenaFragment
import edu.labneo.saludable_tpfinal.fragments.DesayunoFragment
import edu.labneo.saludable_tpfinal.fragments.MeriendaFragment

//Interfaz del ViewPager2
interface ResourceStore {
    companion object {
        val tabList = listOf(
            R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4
        )
        val pagerFragments = listOf(
            DesayunoFragment.create(), AlmuerzoFragment.create(), MeriendaFragment.create(), CenaFragment.create()
        )
    }
}