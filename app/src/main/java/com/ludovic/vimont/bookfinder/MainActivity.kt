package com.ludovic.vimont.bookfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ludovic.vimont.bookfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLaunchBookScan.setOnClickListener {
            val intent = Intent(this, BarcodeCaptureActivity::class.java)
            startActivity(intent)
        }
    }
}