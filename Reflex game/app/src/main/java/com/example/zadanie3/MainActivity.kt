package com.example.zadanie3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random




class MainActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var secondsElapsed: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val random = Random
        var valueP1 = 0
        var valueP2 = 0



        val delayInSeconds = random.nextInt(7)+2
        findViewById<Button>(R.id.player1).isEnabled = false //blokowanie przycisków
        findViewById<Button>(R.id.player2).isEnabled = false


        fun startTimer() {
            handler = Handler()
            runnable = object : Runnable {
                override fun run() {
                    secondsElapsed++
                    findViewById<TextView>(R.id.czas).text = secondsElapsed.toString() // aktualizacja pola tekstowego z czasem
                    handler.postDelayed(this, 1)
                }
            }
            handler.postDelayed(runnable, 1)
        }

        // ...

        fun stopTimer() {
            handler.removeCallbacks(runnable)
            findViewById<TextView>(R.id.czas).text = secondsElapsed.toString() + " ms"
            secondsElapsed = 0
        }

        fun runHandler() {
            if (valueP1 == 3 || valueP2 == 3){
                findViewById<TextView>(R.id.wynik).text = "Koniec gry!"
                if (valueP1 == 3){
                    findViewById<TextView>(R.id.wynik).append("\nWygrał Player 1!")
                } else {
                    findViewById<TextView>(R.id.wynik).append("\nWygrał Player 2!")
                }
                findViewById<View>(R.id.kolokolo).visibility = View.INVISIBLE
                findViewById<Button>(R.id.player1).visibility = View.INVISIBLE
                findViewById<Button>(R.id.player2).visibility = View.INVISIBLE
                findViewById<TextView>(R.id.textView5).visibility = View.INVISIBLE
                findViewById<TextView>(R.id.textView3).visibility = View.INVISIBLE
                findViewById<TextView>(R.id.textView4).visibility = View.INVISIBLE
                findViewById<Button>(R.id.RESET).visibility = View.VISIBLE

                findViewById<Button>(R.id.RESET).setOnClickListener{ //reset przycisk resetuj mi
                    findViewById<View>(R.id.kolokolo).visibility = View.VISIBLE
                    findViewById<Button>(R.id.player1).visibility = View.VISIBLE
                    findViewById<Button>(R.id.player2).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView5).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView3).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView4).visibility = View.VISIBLE
                    findViewById<Button>(R.id.RESET).visibility = View.INVISIBLE
                    findViewById<View>(R.id.czas).visibility = View.INVISIBLE
                    valueP1 = 0
                    valueP2 = 0
                    findViewById<TextView>(R.id.wynik).text = ""
                    runHandler() // handler again
                }

            }
            else{
                Handler().postDelayed({
                    findViewById<View>(R.id.kolokolo).setBackgroundResource(R.drawable.koloziel) //kolory zmiana
                    findViewById<View>(R.id.czas).visibility = View.INVISIBLE // czas schowaj sie
                    startTimer() //czas
                    findViewById<Button>(R.id.player1).isEnabled = true //odblokowanie przycisków
                    findViewById<Button>(R.id.player2).isEnabled = true

                    findViewById<Button>(R.id.player1).setOnClickListener{ //player1
                        findViewById<TextView>(R.id.wynik).text = "Player1"
                        valueP1++ // punkty gracza1
                        findViewById<View>(R.id.kolokolo).setBackgroundResource(R.drawable.kolo);//kolorek kulki
                        findViewById<Button>(R.id.player1).isEnabled = false
                        findViewById<Button>(R.id.player2).isEnabled = false
                        runHandler() //uruchomienie Handler po kliknięciu player1
                        findViewById<View>(R.id.czas).visibility = View.VISIBLE // czas pokaz sie
                        stopTimer()
                    }

                    findViewById<Button>(R.id.player2).setOnClickListener{ //player2
                        findViewById<TextView>(R.id.wynik).text = "Player2"
                        valueP2++ // tu punty gracz2
                        findViewById<View>(R.id.kolokolo).setBackgroundResource(R.drawable.kolo);//kolorek
                        findViewById<Button>(R.id.player1).isEnabled = false
                        findViewById<Button>(R.id.player2).isEnabled = false
                        runHandler() //uruchomienie Handler po kliknięciu player2
                        findViewById<View>(R.id.czas).visibility = View.VISIBLE // czas pokaz sie
                        stopTimer()
                    }

                }, delayInSeconds * 1000L)

            }

        }
        runHandler()
    }
}
