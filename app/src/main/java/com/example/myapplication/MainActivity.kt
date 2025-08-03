package com.example.myapplication

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var mealAdapter: MealAdapter
    private val mealList = mutableListOf<Meal>()
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
            Log.d("ButtonClick", "Button was clicked ✅")
            Toast.makeText(this, "Fetching meal...", Toast.LENGTH_SHORT).show()

        }
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
        // Performs a random call
        val getRandom = findViewById<Button>(R.id.button_random_meal)
        getRandom.setOnClickListener {
            Log.d("RandomButtonClick", "Button was clicked ✅")
            val intent = Intent(this, DishDetailsActivity::class.java)
            intent.putExtra("RandomID", true.toString())
            startActivity(intent)

        }




        val recyclerView = findViewById<RecyclerView>(R.id.rv_list_of_dishes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mealAdapter = MealAdapter(mealList)
        recyclerView.adapter = mealAdapter


    }

    fun fetchMeals(selectedRegion: String, selectedCategory: String) {
        val client = AsyncHttpClient()

        val regionUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?a=${selectedRegion}"
        val categoryUrl = "https://www.themealdb.com/api/json/v1/1/filter.php?c=${selectedCategory}"

        var regionMeals: JSONArray? = null
        var categoryMeals: JSONArray? = null

        client[regionUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                regionMeals = json.jsonObject.getJSONArray("meals")
                Log.d("REGION", "Got ${regionMeals!!.length()} meals from $selectedRegion")

                // Now call category filter
                client[categoryUrl, object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                        categoryMeals = json.jsonObject.getJSONArray("meals")
                        Log.d("CATEGORY", "Got ${categoryMeals!!.length()} meals from $selectedCategory")

                        val regionMealIds = mutableSetOf<String>()
                        for (i in 0 until regionMeals!!.length()) {
                            val id = regionMeals!!.getJSONObject(i).getString("idMeal")
                            regionMealIds.add(id)
                        }

                        mealList.clear()

                        for (i in 0 until categoryMeals!!.length()) {
                            val mealObj = categoryMeals!!.getJSONObject(i)
                            val id = mealObj.getString("idMeal")

                            if (regionMealIds.contains(id)) {
                                val meal = Meal(
                                    id = id,
                                    name = mealObj.getString("strMeal"),
                                    thumbUrl = mealObj.getString("strMealThumb"),
                                    region = selectedRegion,
                                    category = selectedCategory
                                )
                                mealList.add(meal)
                            }
                        }

                        if (mealList.isEmpty()) {
                            Toast.makeText(this@MainActivity, "No meals found matching both filters 😢", Toast.LENGTH_LONG).show()
                        }

                        mealAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                        Log.e("CATEGORY FAIL", "Error: $errorResponse")
                    }
                }]
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.e("REGION FAIL", "Error: $errorResponse")
            }
        }]
    }


}