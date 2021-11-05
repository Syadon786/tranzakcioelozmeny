package com.example.tranzakcioelozmenyek

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class TranzactionManager {
    companion object {

        const val SZALLAS_ID : Int = 0
        const val VENDEGLATAS_ID : Int = 1
        const val SZABADIDO_ID : Int = 2

        private var balances = mutableListOf(0u, 0u, 0u, 0u, 0u)

        fun changeBalance(appContext: Context, amount: UInt, isWithdrawing : Boolean,  actID : Int, editor : SharedPreferences.Editor)
        {
            if(isWithdrawing)
            {
                balances[actID] -= amount
                Toast.makeText(appContext, "Sikeres tranzakció", Toast.LENGTH_SHORT).show()
            }
            else
            {
                balances[actID] += amount
                Toast.makeText(appContext, "Sikeres tranzakció", Toast.LENGTH_SHORT).show()
            }
            saveBalance(editor, balances[actID], actID)
        }

        fun getBalance(id : Int) : UInt
        {
            return balances[id]
        }

        private fun saveBalance(editor : SharedPreferences.Editor, amount : UInt, actID: Int)
        {
            editor.apply {
                putInt(actID.toString(), amount.toInt())
                apply()
            }
        }
        fun loadBalance(sharedPreference : SharedPreferences, id : Int, txtView : TextView?)
        {
            val amount = sharedPreference.getInt(id.toString(), 0)
            balances[id] = amount.toUInt()
            val txt = "Egyenleg ${getBalance(id)} Ft"
            txtView?.text = txt
        }
    }
}