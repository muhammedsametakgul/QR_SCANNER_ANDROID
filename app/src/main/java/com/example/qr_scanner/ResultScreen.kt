package com.example.qr_scanner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class ResultScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_screen)
        val textView=findViewById<TextView>(R.id.txtResult)
        val copy =findViewById<ImageButton>(R.id.copyButton)
        val open=findViewById<ImageButton>(R.id.openButton)


        //get the data that comes from MainActivity
        var a=intent.getStringExtra("Key")
        textView.text=a.toString()

        copy.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", a)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this,"Başarıyla Kopyalndı",Toast.LENGTH_SHORT).show()
        }

        open.setOnClickListener {
        val url=a.toString()
            val urlIntent=Intent(Intent.ACTION_VIEW, Uri.parse(a))
            startActivity(urlIntent)
        }

    }
}