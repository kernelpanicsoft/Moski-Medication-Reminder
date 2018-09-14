package com.kps.spart.moskimedicationreminder

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Toast

import java.util.ArrayList


class DirectorioFragment : Fragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_directorio, container, false)

        viewPager = v.findViewById(R.id.ViewPagerDirectorio)
        setupViewPager(viewPager!!)

        tabLayout = v.findViewById(R.id.TabLayoutDirectorio)
        tabLayout!!.setupWithViewPager(viewPager)

        return v
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MedicosFragment(), getString(R.string.Medicos))
        adapter.addFragment(FarmaciasFragment(), getString(R.string.Establecimientos))

        pager.adapter = adapter
    }

    private inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.itemADD -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.SeleccionAnadir)
                        .setItems(R.array.anadirnuevo) { dialog, which ->
                            val nav: Intent
                            if (which == 0) {
                                nav = Intent(context, AnadirMedicoActivity::class.java)
                                startActivity(nav)
                            } else if (which == 1) {
                                nav = Intent(context, AnadirFarmaciaActivity::class.java)
                                startActivity(nav)
                            }
                        }
                val dialog = builder.create()
                dialog.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}// Required empty public constructor
