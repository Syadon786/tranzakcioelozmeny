package com.example.tranzakcioelozmenyek

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(context: Context ,fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    val appContext = context;

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 1)
        {
            return Bankkartya()
        }
        else if(position == 2)
        {
            return Tranzakciok()
        }
        return Zsebek(appContext)
    }

}