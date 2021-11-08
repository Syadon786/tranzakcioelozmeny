package com.example.tranzakcioelozmenyek.adapter

import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.tranzakcioelozmenyek.R
import com.example.tranzakcioelozmenyek.model.Tranzaction
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TranzactionManager {
    companion object {

        const val SZALLAS_ID : Int = 0
        const val VENDEGLATAS_ID : Int = 1
        const val SZABADIDO_ID : Int = 2
        const val KARTYA_ID : Int = 3
        const val KESZPENZ_ID : Int = 4
        private const val tranzactionHistory : String = "tranzactions.txt"

        private var balances = mutableListOf(0u, 0u, 0u, 0u, 0u)

        fun changeBalance(appContext: Context, am: String, comment: String, isWithdrawing : Boolean, actID : Int, editor : SharedPreferences.Editor) : Boolean
        {

            if(am == "")
            {
                Toast.makeText(appContext, "Nem adott meg összeget", Toast.LENGTH_SHORT).show()
                return false
            }
            else if( am.startsWith('-'))
            {
                Toast.makeText(appContext, "Pozitív számot kell megadni", Toast.LENGTH_SHORT).show()
                return false
            }

            val amount : UInt = am.toUInt()

            if(amount == 0u) {
                Toast.makeText(appContext, "Nem adott meg összeget", Toast.LENGTH_SHORT).show()
                return false
            }

            if(isWithdrawing)
            {
                if(amount > balances[actID]) {
                    Toast.makeText(appContext, "Ennyi pénz nem áll rendelkezésre", Toast.LENGTH_SHORT).show()
                    return false
                }
                balances[actID] -= amount
            }
            else
            {
                balances[actID] += amount
            }

            saveBalance(editor, balances[actID], actID)
            saveTransaction(appContext, am, comment, actID, isWithdrawing)

            Toast.makeText(appContext, "Sikeres tranzakció", Toast.LENGTH_SHORT).show()
            return true

        }

        fun getBalance(id : Int) : UInt
        {
            return balances[id]
        }


        fun saveTransaction(context: Context, amount : String ,comment : String, id : Int, isWithdrawing: Boolean)
        {
            val category : String
            when(id) {
                0 -> category = context.getString(R.string.szallashely)
                1 -> category = context.getString(R.string.vendeglatas)
                2 -> category = context.getString(R.string.szabadido)
                3 -> category = context.getString(R.string.bankkartya)
                else -> category = context.getString(R.string.keszpenz)
            }
            val currentDateTime : String =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                LocalDateTime.now(ZoneId.of("Europe/Budapest")).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
            } else {
                "Hiba"
            }

            val text = if(comment == "") "$currentDateTime\t \t$amount Ft - $category\t$isWithdrawing\n"  else "$currentDateTime\t$comment\t$amount Ft - $category\t$isWithdrawing\n"
            val fileObject = File(context.filesDir, tranzactionHistory)
            if(fileObject.exists())
            {
                fileObject.appendText(text)
            }
            else
            {
                fileObject.createNewFile()
                fileObject.appendText(text)
            }
        }

        fun loadTransactions(context: Context) : List<Tranzaction>
        {

            val trHistory = mutableListOf<Tranzaction>()
            val fileObject = File(context.filesDir, tranzactionHistory)
            if(fileObject.exists()) {
                val bufferedReader = fileObject.bufferedReader()
                val lines : List<String> = bufferedReader.readLines()
                for (line in lines.reversed())
                {
                    val act = line.split("\t")
                    trHistory.add(Tranzaction((act[0]+ " " + act[1] + " " + act[2]), act[3].toBoolean()))
                }
            }
            return trHistory
        }

        fun deleteTransactionHistory(context : Context)
        {
            val fileObject = File(context.filesDir, tranzactionHistory)
            fileObject.delete()
            Toast.makeText(context, "A törlés sikeres volt", Toast.LENGTH_SHORT).show()
        }

        private fun saveBalance(editor : SharedPreferences.Editor, amount : UInt, actID: Int)
        {
            editor.apply {
                putInt(actID.toString(), amount.toInt())
                apply()
            }
        }
        fun loadBalances(sharedPreference : SharedPreferences, id : Int, txtView : TextView?)
        {
            val amount = sharedPreference.getInt(id.toString(), 0)
            balances[id] = amount.toUInt()
            val txt = "Egyenleg ${getBalance(id)} Ft"
            txtView?.text = txt
        }
    }
}