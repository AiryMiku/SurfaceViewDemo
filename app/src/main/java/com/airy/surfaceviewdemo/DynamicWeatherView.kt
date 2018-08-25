package com.airy.surfaceviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView


/**
 * Created by Airy on 2018/8/25
 * Mail: a532710813@gmail.com
 * Github: AiryMiku
 */

class DynamicWeatherView : SurfaceView,SurfaceHolder.Callback{

    interface WeatherType{
        fun onDraw(canvas: Canvas)
        fun onSizeChanged(context: Context,width: Int,height: Int)
    }

    inner class DrawThread : Thread(){

        var isRunning: Boolean = false

        override fun run() {
            var canvas: Canvas
            // run and run
            while (isRunning) {
                if (mType != null && mViewHeight != 0 && mViewWidth != 0) {
                    canvas = mHolder!!.lockCanvas()
                    if (canvas != null) {
                        mType?.onDraw(canvas)
                        if (isRunning) {
                            mHolder!!.unlockCanvasAndPost(canvas)
                        } else {
                            break
                        }
                    }
                    //
                    SystemClock.sleep(1)
                }
            }
        }
    }

    private var mContext: Context? = null
    private var mHolder: SurfaceHolder? = holder
    private var mDrawThread: DrawThread? = null
    private var mViewHeight: Int? = 0
    private var mViewWidth: Int? = 0
    var mType: WeatherType? = null


    constructor(context : Context) : super(context)

    constructor(context : Context,attributeSet: AttributeSet) : this(context,attributeSet,0)

    constructor(context : Context,attributeSet: AttributeSet,defStyleAttr : Int)
            : super(context,attributeSet,defStyleAttr){
        mContext = context
        mHolder = holder
        mHolder!!.addCallback(this)
        mHolder!!.setFormat(PixelFormat.TRANSPARENT)
        if (mType != null)
            mType!!.onSizeChanged(context,width,height)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewHeight = h
        mViewWidth = w
        mType!!.onSizeChanged(this.mContext!!,w,h)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mDrawThread!!.isRunning = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mDrawThread = DrawThread()
        mDrawThread!!.isRunning = true
        mDrawThread!!.start()
    }


}