package com.manucg.pongkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.manucg.pongkotlin.sprites.Sprite
import java.util.*


abstract class GameView(context: Context?) : SurfaceView(context), Runnable {
    var hilo: Thread? = null

    @Volatile
    protected var enEjecucion: Boolean = false
    var pausado: Boolean = true
    var mSurfaceHolder: SurfaceHolder? = null
    var canvas: Canvas? = null
    var paint: Paint? = null
    var mScreenX = 0
    var mScreenY = 0

    var FPS: Long = 0
    private var ultimoProceso: Long = 0
    private val PERIODO_PROCESO = 30
    var factor_mov = 0f
    var ahora: Long = 0
    var tiempo_transcurrido: Long = 0

    private val actores: LinkedList<Sprite> = LinkedList<Sprite>()
    var nuevos: LinkedList<Sprite> = LinkedList<Sprite>()

    constructor (context: Context?, x: Int, y: Int) : this(context) {
        //inicializar tamaño de pantalla
        mScreenX = x
        mScreenY = y
        //inicializar objetos de dibujo
        mSurfaceHolder = this.holder
        paint = Paint()
    }

    open fun getActores(): LinkedList<Sprite>? {
        return this.actores
    }

    open fun setupActores() {
        for (actor in getActores()!!) {
            if (actor.isVisible()) actor.reset()
        }
    }

    override fun run() {
        while (enEjecucion) {
            if (!pausado) {
                updateGame()
            }
        }
    }

    open fun resume() {
        enEjecucion = true
        hilo = Thread(this)
        hilo!!.start()
    }

    open fun pause() {
        enEjecucion = false
        try {
            hilo!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    open fun updateGame() {
        ahora = System.currentTimeMillis() //100000
        if (ultimoProceso + PERIODO_PROCESO > ahora) {   //0+200>100000?? No
            return
        }
        tiempo_transcurrido = ahora - ultimoProceso //tiempo_transcurrido=1000000-0
        factor_mov =
            (tiempo_transcurrido / PERIODO_PROCESO).toFloat() //factor_mov=100000 el primero no es realista
        ultimoProceso = ahora
        actualizaActores()
        limpiaActores()
        draw()
    }

    open fun actualizaActores() {
        //recalculamos los valores de posición actualizada de los actores
        for (actor in getActores()!!) {
            if (actor.isVisible()) actor.update(this, FPS.toFloat())
        }
        getActores()!!.addAll(nuevos)
        nuevos = LinkedList()
    }

    open fun pintaActores() {
        synchronized(getActores()!!) {
            for (actor in getActores()!!) {
                actor.pintaSprite(canvas)
            }
        }
    }

    open fun limpiaActores() {
        synchronized(getActores()!!) {
            for (actor in getActores()!!) if (!actor.isVisible()) getActores()!!.remove(actor)
        }
    }

    open fun draw() {
        //comprobar si la superficie a pintar es válida
        if (mSurfaceHolder!!.surface.isValid) {

            //Se empieza a pintar. Hay que bloquear el canvas a pintar
            canvas = mSurfaceHolder!!.lockCanvas()
            dibuja(canvas)

            // Se desbloquea el canvas añadiendo lo que se ha pintado
            mSurfaceHolder!!.unlockCanvasAndPost(canvas)
        }
    }
    abstract fun dibuja(canvas: Canvas?)
}
