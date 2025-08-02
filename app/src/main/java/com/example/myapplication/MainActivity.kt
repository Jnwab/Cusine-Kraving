package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Spinner Adapter for REGION option
        val regionList = resources.getStringArray(R.array.regions_array)
        val spinnerRegion: Spinner = findViewById<Spinner>(R.id.spinner_cuisine_option)
        spinnerRegion.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, regionList)

        // Spinner Adapter for CATEGORY option
        val categoryList = resources.getStringArray(R.array.categories_array)
        val spinnerCategory: Spinner = findViewById<Spinner>(R.id.spinner_category_option)
        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)


        // BUTTON to FETCH MEAL
        val getCuisine = findViewById<Button>(R.id.button_get_cuisine)
        getCuisine.setOnClickListener {
            val regionIndex = spinnerRegion.selectedItemPosition
            val categoryIndex = spinnerCategory.selectedItemPosition
            // ERROR HANDLE: Toast if User does not select region and/or category
            if (regionIndex == 0 || categoryIndex == 0) {
                Toast.makeText(this, "Please select a region AND a category.", Toast.LENGTH_LONG).show()
            }
            else {
                val selectedRegion = spinnerRegion.selectedItem.toString()
                val selectedCategory = spinnerCategory.selectedItem.toString()
                fetchMeals(selectedRegion, selectedCategory)
            }
        }
    }

    fun fetchMeals(selectedRegion: String, selectedCategory: String){

        val client = AsyncHttpClient()

        client["https://www.themealdb.com/api/json/v1/1/random.php", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Meal", "response successful: $json")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }
}