package com.manucg.pongkotlin.sprites

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.manucg.pongkotlin.GameView

abstract class Sprite(screenX : Int, screenY : Int) {
    //tamaño de la pantalla
    var mScreenX = screenX
    var mScreenY = screenY

    //Velocidades de eje X y eje Y de partida
    open var velInicialX : Float ?= null
    open var velInicialY : Float ?= null

    //velocidades actuales
    open var velActualX : Float ?= null
    open var velActualY : Float ?= null

    //la imagen se pinta o no se pinta y permanece oculta
    var visible = true
    open var color = Color.argb(255, 255, 255, 255)

    var paint = Paint()

    //Para controlar las colisiones
    protected open var actores = mutableListOf<Sprite>()

    open fun isVisible(): Boolean {
        return visible
    }

    constructor(screenX: Int, screenY : Int, x : Int, y : Int) : this(screenX, screenY) {
        mScreenX = screenX
        mScreenY = screenY
        color = Color.argb(255, 255, 255, 255)
        paint = Paint()
    }
    open fun addListenerColision(s: Sprite?) {
        actores.add(s!!)
    }

    abstract fun colision(s: Sprite?): Boolean
    abstract fun colisionBordeLeft(): Boolean
    abstract fun colisionBordeRight(): Boolean
    abstract fun colisionBordeTop(): Boolean
    abstract fun colisionBordeBottom(): Boolean
    abstract fun recolocaX(x: Float)
    abstract fun recolocaY(y: Float)
    open fun recolocaXY(x: Float, y: Float) {

    }

    open fun pintaSprite(canvas: Canvas?) {}

    abstract fun reset()
    abstract fun update(game: GameView?, fps: Float)
}
