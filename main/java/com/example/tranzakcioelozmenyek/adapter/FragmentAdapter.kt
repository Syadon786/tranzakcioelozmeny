package com.example.tranzakcioelozmenyek.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tranzakcioelozmenyek.BankkartyaFragment
import com.example.tranzakcioelozmenyek.TranzakciokFragment
import com.example.tranzakcioelozmenyek.ZsebekFragment

class FragmentAdapter(context: Context ,fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val appContext = context;

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 1)
        {
            return BankkartyaFragment()
        }
        else if(position == 2)
        {
            return TranzakciokFragment(appContext)
        }
        return ZsebekFragment(appContext)
    }

}