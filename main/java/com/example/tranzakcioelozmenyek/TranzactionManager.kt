package com.example.tranzakcioelozmenyek

import android.content.Context
import android.widget.TextView
import android.widget.Toast

class TranzactionManager {
    companion object {

        val SZALLAS_ID : Byte = 0
        val VENDEGLATAS_ID : Byte = 1
        val SZABADIDO_ID : Byte = 2

        fun changeBalance(appContext: Context, balanceText : TextView?, isWithdrawing : Boolean, wId : Byte)
        {
            Toast.makeText(appContext, "Működik", Toast.LENGTH_SHORT).show()
        }
        private fun saveBalance(ac : Context)
        {

        }
        private fun loadBalance(ac : Context)
        {

        }
    }
}