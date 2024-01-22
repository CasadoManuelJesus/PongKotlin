package com.manucg.pongkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import com.manucg.pongkotlin.GameView
import com.manucg.pongkotlin.sprites.Bola

class Pong(context: Context?, x: Int, y: Int) : GameView(context, x, y) {
    //Actores del juego
    var bola: Bola? = null

    //variables del juego
    var puntuacion :Int = 0
    var vidas:Int = 0
    init {
        setupPong()
    }

    private fun setupPong() {
        //actores del juego
        bola = Bola(mScreenX, mScreenY)
        getActores()!!.add(bola!!)

        //Inicializa los valores del juego.
        if (vidas == 0) {
            puntuacion = 0
            vidas = 3
        }
        setupActores()
    }

    override fun dibuja(canvas: Canvas?) {
        //se pinta desde la capa más lejana hasta la más cercana
        canvas!!.drawColor(Color.argb(255, 20, 128, 188))

        //pintamos los actores del juego
        pintaActores()

        //dibujamos puntuacion y vidas
        paint!!.textSize = 30F
        canvas.drawText(
            "Factor_mov: " + factor_mov.toString() + "  Vidas: " + getActores()!!.size, 10f, 50f,
            paint!!
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                pausado = false
                if (event.x < mScreenX / 2) {
                    bola = Bola(mScreenX, mScreenY)
                    bola!!.color= Color.argb(255, 0, 255, 0)
                    nuevos.add(bola!!)
                    this.puntuacion++
                } else {
                    bola = Bola(mScreenX, mScreenY)
                    bola!!.color= Color.argb(255, 255, 0, 0)
                    nuevos.add(bola!!)
                    this.puntuacion++
                }
            }
            MotionEvent.ACTION_UP -> {}
        }
        return true
    }
}