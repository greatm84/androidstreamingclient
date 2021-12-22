package com.kikikoko.android.sockettest

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.net.Socket

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        displayImage()
    }

    private fun displayImage() {
        Thread {
            val socket = Socket("192.168.0.128", 9090)
            while (true) {
                val bitmap = BitmapFactory.decodeStream(socket.getInputStream())

                Log.e("KSH_TEST", "${bitmap.byteCount}")

                Handler(Looper.getMainLooper()).post {
                    Log.e("KSH_TEST", "set Image ${bitmap.width} ${bitmap.height}")
                    imageView.setImageBitmap(bitmap)
                }
            }
            socket.close()
        }.start()
    }
}