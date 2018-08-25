package com.airy.surfaceviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable


/**
 * Created by Airy on 2018/8/25
 * Mail: a532710813@gmail.com
 * Github: AiryMiku
 */

class RainTypeImpl : BaseType{

    private var mBackground: Drawable? = null
    private var mRains : ArrayList<RainHolder>? = null
    private var mPaint: Paint? = null

    constructor (context: Context,dynamicWeatherView: DynamicWeatherView)
            :super(context,dynamicWeatherView){
        initPaint()
    }

    private fun initPaint(){
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = Color.WHITE
        // 宽
        mPaint!!.strokeWidth = 3F
        mRains = ArrayList()
    }

    override fun generate() {
        mBackground = mContext!!.resources.getDrawable(R.drawable.sky_night)
        mBackground!!.setBounds(0,0, this.mWidth!!, this.mHeight!!)
        for (i in 1..60){
            var rain = RainHolder(
                    getRandom(1, this.mWidth!!),
                    getRandom(1, this.mHeight!!),
                    getRandom(dp2px(9F),dp2px(15F)),
                    getRandom(dp2px(5F),dp2px(9F)),
                    getRandom(20,100)
            )
            mRains!!.add(rain)
        }
    }

    override fun onDraw(canvas: Canvas){
        clearCanvas(canvas)
        mBackground!!.draw(canvas)
        // 画雨点
        for (r in this.mRains!!) {
            mPaint!!.alpha = r.a
            canvas.drawLine(r.x.toFloat(), r.y.toFloat(), r.x.toFloat(), (r.y + r.l).toFloat(), mPaint)
        }

        // 速度偏移
        for (r in this.mRains!!) {
            r.y = r.y.plus(r.s)
            if (r.y > this.mHeight!!) {
                r.y = -r.l
            }
        }
    }

    /**
     * x y坐标 l 长度 s 速度 a 透明度
     */
    inner class RainHolder(var x: Int, var y: Int, var l: Int, var s: Int, var a: Int)
}