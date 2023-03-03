package com.example.qr_scanner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback

class MainActivity : AppCompatActivity() {
    private lateinit var  codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scannerView =findViewById<CodeScannerView>(R.id.qr_scanner)

        //İzinler alındı
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                val permissionList= arrayOf(android.Manifest.permission.CAMERA)
                requestPermissions(permissionList,1)
            }
        }
        //CodeScanner özellikleri tanımlandı
        codeScanner = CodeScanner(this@MainActivity,scannerView)
        codeScanner.camera =CodeScanner.CAMERA_BACK
        codeScanner.formats =CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode= AutoFocusMode.SAFE
        codeScanner.isAutoFocusEnabled=true
        codeScanner.isFlashEnabled=false

        codeScanner.decodeCallback= DecodeCallback {
            runOnUiThread{
                //Toast.makeText(this,"Sonuç =${it.text}", Toast.LENGTH_LONG).show()
                //take the data to ResultActivty
                val intent=Intent(this,ResultScreen::class.java)
                intent.putExtra("Key", it.text)
                startActivity(intent)
            }
        }
        codeScanner.errorCallback= ErrorCallback {
            runOnUiThread {
                Toast.makeText(this,"HATA :${it.message}", Toast.LENGTH_LONG).show()

            }
        }
        //Ekrana tıklanınca
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    //Uygulama devam ediyorken

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }
    //Uygulama durduğunda
    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }
}