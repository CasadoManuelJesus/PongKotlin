package com.manucg.pongkotlin.sprites


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import com.manucg.pongkotlin.GameView
import com.manucg.pongkotlin.Pong
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*

class Bola(x: Int, y: Int) : SpriteRect(x, y) {


    override var color = Color.argb(255, 255, 255, 255)
    override var ancho = mScreenX / 35f
    override var alto = ancho
    override var mRect = RectF(mXCoord, mYCoord, mXCoord + ancho, mYCoord + alto)

    val mult = (Math.random() * 360) + 1
    override var velInicialX: Float? = mScreenY / 100f
    override var velInicialY: Float? = mScreenY / 100f

    override var velActualX: Float? = (velInicialX?.times(cos(Math.toRadians((Math.random() * 180)))))?.toFloat()
    override var velActualY: Float? = (velInicialY?.times(sin(Math.toRadians((Math.random() * 180)))))?.toFloat()

    override fun colision(s: Sprite?): Boolean {
        return if (s is SpriteRect) colisionRect(s) else false
    }

    fun invertirVelX() {
        val mult = (Math.random() * 180) + 1
        velActualX = (-velInicialX!! * cos(Math.toRadians(mult))).toFloat()
    }

    fun invertirVelY() {
        val mult = (Math.random() * 180) + 1
        velActualY = (-velInicialY!! * sin(Math.toRadians(mult))).toFloat()
    }

    fun setRandomXVelocity() {
        val random = Random()
        val addVelocity = random.nextInt(2)
        velActualX = velActualX!! + addVelocity
        if (addVelocity == 0) invertirVelX()
    }

    fun incrementaVelocidad() {
        velActualX = velActualX!! * 1.1f
        velActualY = velActualY!! * 1.1f
    }

    override fun reset() {
        mRect.left = mScreenX / 2f
        mRect.right = mScreenX / 2f + ancho
        mRect.top = mScreenY - 500f
        mRect.bottom = mScreenY - 20 + alto
        velActualX = velInicialX
        velActualY = velInicialY
    }

    override fun update(game: GameView?, fps: Float) {
        val pong = game as Pong

        mRect.left += velActualX!! * pong.factor_mov
        mRect.top += velActualY!! * pong.factor_mov
        mRect.right = mRect.left + ancho
        mRect.bottom = mRect.top + alto

        val objetos = game!!.getActores()

        for (objeto in objetos!!) {
            if (!objeto.equals(this)) {
                if (objeto.isVisible() && colision(objeto)) {
                    if (objeto is Bola) {
                        objeto.setRandomXVelocity()
                        objeto.invertirVelY()
                        objeto.recolocaY(objeto.getRect().top - 2)
                        setRandomXVelocity()
                        invertirVelY()
                    }
                }
            }

            if (colisionBordeRight()) {
                invertirVelX()
                recolocaX(mScreenX - ancho - 80)
            }
            if (colisionBordeLeft()) {
                invertirVelX()
                recolocaX(80f)
            }

            if (colisionBordeTop()) {
                invertirVelY()
                recolocaY(90f)
            }

            if (colisionBordeBottom()) {
                invertirVelY()
                recolocaY(mScreenY - 90f)
                pong.vidas--
                if (pong.vidas == 0) {
                    // pong.pausado = true;
                    // pong.setupGame();
                }
            }
        }

        fun pinta(canvas: Canvas) {
            paint.color = color
            val centroX = ancho / 2 + mRect.left
            val centroY = alto / 2 + mRect.top
            canvas.drawCircle(centroX, centroY, ancho / 2, paint)
        }

        fun setColorRed() {
            color = Color.argb(255, 255, 0, 0)
        }

        fun setColorGreen() {
            color = Color.argb(255, 0, 255, 0)
        }
    }
}
