package com.manucg.pongkotlin

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var juego: GameView

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display = this.display
        val size = Point()
        display?.getSize(size)
        //gameView=new GameView(this,area.right-area.left,area.bottom-area.top);
        juego = Pong(this, size.x, size.y)
        setContentView(juego)
    }

    override fun onResume() {
        super.onResume()
        juego.resume()
    }

    override fun onPause() {
        super.onPause()
        juego.pause()
    }
}
