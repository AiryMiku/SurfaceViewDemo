package com.airy.surfaceviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import java.util.*

/**
 * Created by Airy on 2018/8/25
 * Mail: a532710813@gmail.com
 * Github: AiryMiku
 */

abstract class BaseType : DynamicWeatherView.WeatherType{

    var mContext: Context? = null
    var mWidth: Int? = null
    var mHeight: Int? = null

    abstract fun generate()

    constructor (context: Context, dynamicWeatherView: DynamicWeatherView){
        mContext = context
        mWidth = dynamicWeatherView.width
        mHeight = dynamicWeatherView.height
    }

    constructor()

    /**
     * 清空画布
     */
    fun clearCanvas(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR)
    }

    override fun onSizeChanged(context: Context, width: Int, height: Int) {
        mWidth = width
        mHeight = height
        // 重新生成大小
        generate()
    }

    protected fun getRandom(min: Int,max: Int): Int {
        if (max < min)
            return 1

        return min + Random().nextInt(max - min)
    }

    fun dp2px(dpValue: Float) :Int{
        val scale: Float = mContext!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}