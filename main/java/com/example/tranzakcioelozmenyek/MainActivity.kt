package com.example.tranzakcioelozmenyek

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.tranzakcioelozmenyek.adapter.FragmentAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val fm = supportFragmentManager
        val fragmentAdapter = FragmentAdapter(applicationContext, fm, lifecycle)

        viewPager2.adapter = fragmentAdapter

        tabLayout!!.addTab(tabLayout.newTab().setText(R.string.szepkartyafelirat))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.bankkartyafelirat))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.elozmenyekfelirat))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}