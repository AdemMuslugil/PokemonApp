package com.adem.pokemonapp.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.adem.pokemonapp.R
import com.adem.pokemonapp.util.CustomSharedPreferences


class OverlayService : Service(), View.OnTouchListener,View.OnClickListener{

    private lateinit var overlayTextView : TextView
    private lateinit var overlayButton: Button
    private lateinit var layout : LinearLayout
    private lateinit var overlayImageBack: ImageView
    private lateinit var overlayImageFront: ImageView
    private lateinit var windowManager: WindowManager
    private lateinit var params : WindowManager.LayoutParams

    private var customSharedPreferences = CustomSharedPreferences()


    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0.0f
    private var initialTouchY = 0.0f
    private var moving = false


    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        overlayImageFront = ImageView(this)
        //overlayImageFront.downloadImage(customSharedPreferences.getOverlayData()[1]!!, placeHolderProgressBar(this))
        overlayImageFront.setImageResource(R.mipmap.ic_launcher)

        overlayImageBack = ImageView(this)
        //overlayImageBack.downloadImage(customSharedPreferences.getOverlayData()[2]!!, placeHolderProgressBar(this))
        overlayImageBack.setImageResource(R.mipmap.ic_launcher)

        overlayTextView = TextView(this)
        overlayTextView.text = customSharedPreferences.getOverlayData()[0]
        overlayTextView.gravity = Gravity.CENTER

        overlayButton = Button(this)
        overlayButton.setOnClickListener(this)
        overlayButton.text = "Close Window"

        layout = LinearLayout(this)
        layout.setOnTouchListener(this)
        layout.setBackgroundColor(Color.WHITE)
        layout.orientation = 1
        layout.gravity = Gravity.CENTER


        layout.addView(overlayImageFront)
        layout.addView(overlayImageBack)
        layout.addView(overlayTextView)
        layout.addView(overlayButton)



        val layoutFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //API LEVEL >= 26
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }else{
            //API LEVEL <26
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlags,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.height = resources.displayMetrics.heightPixels / 3
        params.width = resources.displayMetrics.widthPixels / 2
        params.gravity = Gravity.CENTER
        windowManager.addView(layout,params)


        overlayButton.setOnClickListener {
            val service = Intent(this, OverlayService::class.java)
            it.context.stopService(service)
        }
    }



    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

        p0!!.performClick()

        when(p1!!.action){
            MotionEvent.ACTION_DOWN -> {
                initialX = params.x
                initialY = params.y
                initialTouchX = p1.rawX
                initialTouchY = p1.rawY
                moving = true
            }
            MotionEvent.ACTION_UP -> {
                moving = false
            }
            MotionEvent.ACTION_MOVE -> {
                params.x = initialX + (p1.rawX - initialTouchX).toInt()
                params.y = initialY + (p1.rawY - initialTouchY).toInt()
                windowManager.updateViewLayout(layout,params)
            }
        }
        return true
    }

    override fun onClick(p0: View?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(layout)


    }

}