package com.ludovic.vimont.bookfinder

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.ludovic.vimont.bookfinder.databinding.ActivityBarcodeCaptureBinding
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException

class BarcodeCaptureActivity: AppCompatActivity() {
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var binding: ActivityBarcodeCaptureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarcodeCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBarcodeDetector()
        askPermissions()
    }

    private fun initBarcodeDetector() {
        barcodeDetector = BarcodeDetector.Builder(applicationContext)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        if (!barcodeDetector.isOperational) {
            println("Could not set up the detector!")
            return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(125)
    private fun askPermissions() {
        val permissions = arrayOf( Manifest.permission.CAMERA )
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            setupCamera(barcodeDetector, binding.surfaceViewScanDisplay)
            setupBarcodeDetector(barcodeDetector, binding.textViewScanResult)
        } else {
            EasyPermissions.requestPermissions(this, "We need your camera to be able to scan and find the book.", 125, Manifest.permission.CAMERA)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupCamera(detector: BarcodeDetector, cameraView: SurfaceView) {
        val cameraSource = CameraSource.Builder(this, detector)
            .setAutoFocusEnabled(true)
            .build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(cameraView.holder)
                } catch (ie: IOException) {
                    println(ie.message)
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
    }

    private fun setupBarcodeDetector(detector: BarcodeDetector, textViewISBN: TextView) {
        detector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    val bookIsbn = barcodes.valueAt(0).displayValue.toString()
                    runOnUiThread {
                        textViewISBN.text = bookIsbn
                    }
                }
            }
        })
    }
}