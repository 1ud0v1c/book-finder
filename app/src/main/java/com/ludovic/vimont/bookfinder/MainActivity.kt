package com.ludovic.vimont.bookfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ludovic.vimont.bookfinder.databinding.ActivityMainBinding
import com.ludovic.vimont.bookfinder.model.GoogleBooksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object {
        const val FIND_ISBN_CODE = 150
    }
    private var bookIsbn: String = ""
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLaunchBookScan.setOnClickListener {
            val intent = Intent(this, BarcodeCaptureActivity::class.java)
            startActivityForResult(intent, FIND_ISBN_CODE)
        }

        binding.buttonFetchBookData.setOnClickListener {
            if (bookIsbn.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    downloadBook(bookIsbn)
                }
            }
        }
    }

    private suspend fun downloadBook(textViewISBN: String) {
        val googleBooksAPI = RetrofitBuilder.buildRetrofitForAPI(
            GoogleBooksAPI.baseURL,
            GoogleBooksAPI::class.java
        )
        val response: Response<GoogleBooksResponse> = googleBooksAPI.get("isbn:$textViewISBN")
        if (response.isSuccessful) {
            response.body()?.let { googleBooksResponse: GoogleBooksResponse ->
                println(googleBooksResponse.items?.get(0)?.volumeInfo?.title)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FIND_ISBN_CODE && resultCode == RESULT_OK) {
            bookIsbn = data?.getStringExtra("isbn") ?: ""
        }
    }
}