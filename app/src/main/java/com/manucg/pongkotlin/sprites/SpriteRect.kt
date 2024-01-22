package com.manucg.pongkotlin.sprites

import android.graphics.Canvas
import android.graphics.RectF
import com.manucg.pongkotlin.GameView

abstract class SpriteRect(screenX: Int, screenY: Int) : Sprite(screenX, screenY) {

    // rectángulo que se pinta con la imagen
    protected open var mRect: RectF = RectF()

    // Coordenadas x, y esquina superior izquierda
    protected var mXCoord: Float = 0.0f
    protected var mYCoord: Float = 0.0f

    // ancho y alto de la imagen que también se usa para RectF
    protected open var ancho: Float = 0.0f
    protected open var alto: Float = 0.0f

    // Para controlar las colisiones
    override var actores: MutableList<Sprite> = mutableListOf()

    fun getRect(): RectF {
        return mRect
    }

    fun addListenerColision(s: SpriteRect) {
        actores.add(s)
    }

    fun colisionRect(s: SpriteRect): Boolean {
        return mRect.intersect(s.getRect())
    }

    override fun colisionBordeLeft(): Boolean {
        return mRect.left < 0
    }

    override fun colisionBordeRight(): Boolean {
        return mRect.right > mScreenX
    }

    override fun colisionBordeTop(): Boolean {
        return mRect.top < 0
    }

    override fun colisionBordeBottom(): Boolean {
        return mRect.bottom > mScreenY
    }

    override fun recolocaX(x: Float) {
        mRect.left = x
        mRect.right = x + ancho
    }

    override fun recolocaY(y: Float) {
        mRect.bottom = y
        mRect.top = y - alto
    }

    override fun recolocaXY(x: Float, y: Float) {
        mRect.left = x
        mRect.right = x + ancho
        mRect.bottom = y
        mRect.top = y - alto
    }

    override fun pintaSprite(canvas: Canvas?) {
        if (isVisible()) {
            paint.color = color
            canvas!!.drawRect(getRect(), paint)
        }
    }

    abstract override fun reset()
    abstract override fun update(game: GameView?, fps: Float)
}