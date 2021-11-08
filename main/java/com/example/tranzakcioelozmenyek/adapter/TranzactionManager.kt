package com.example.tranzakcioelozmenyek.adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.TextView
import android.widget.Toast
import com.example.tranzakcioelozmenyek.model.Tranzaction
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TranzactionManager {
    companion object {

        const val SZALLAS_ID : Int = 0
        const val VENDEGLATAS_ID : Int = 1
        const val SZABADIDO_ID : Int = 2
        private const val tranzactionHistory : String = "/res/tranzactions.txt"

        private var balances = mutableListOf(0u, 0u, 0u, 0u, 0u)

        fun changeBalance(appContext: Context, am: String, comment: String, isWithdrawing : Boolean, actID : Int, editor : SharedPreferences.Editor) : Boolean
        {

            if(am == "")
            {
                Toast.makeText(appContext, "Nem adott meg összeget", Toast.LENGTH_SHORT).show()
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
            saveTransaction(am, comment, isWithdrawing)

            Toast.makeText(appContext, "Sikeres tranzakció", Toast.LENGTH_SHORT).show()
            return true

        }

        fun getBalance(id : Int) : UInt
        {
            return balances[id]
        }


        fun saveTransaction(amount : String ,comment : String, isWithdrawing: Boolean)
        {

            val currentDateTime : String =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                LocalDateTime.now(ZoneId.of("Europe/Budapest")).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
            } else {
                "Hiba"
            }
            val text = "$currentDateTime\t$comment\t$amount\t$isWithdrawing"
            val fileObject = File(tranzactionHistory)
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

        fun loadTransactions() : List<Tranzaction>
        {
            val trHistory = mutableListOf<Tranzaction>()
            val fileObject = File(tranzactionHistory)
            if(fileObject.exists()) {
               val bufferedReader = fileObject.bufferedReader()
               val lines : List<String> = bufferedReader.readLines()
                for (line in lines)
                {
                    val act = line.split("\t")
                    trHistory.add(Tranzaction((act[0]+ " " + act[1] + " " + act[2]), act[3].toBoolean()))
                }
            }
            return trHistory
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