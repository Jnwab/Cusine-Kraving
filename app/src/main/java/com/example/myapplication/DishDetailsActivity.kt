package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class DishDetailsActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var randomcomfirm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_details)

        //  Get meal ID from intent
        mealId = intent.getStringExtra("MEAL_ID") ?: ""
        randomcomfirm = intent.getStringExtra("RandomID")?: ""
        if (mealId.isNotEmpty()) {
            fetchMealDetails(mealId)
        } else if(randomcomfirm.isNotEmpty()){
            fetchRandomMeal()
        }
        else {
            Toast.makeText(this, "Invalid meal ID 😵‍💫", Toast.LENGTH_SHORT).show()
            finish()
        }

        //  Back button
        findViewById<Button>(R.id.button_go_back).setOnClickListener {


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    private fun fetchMealDetails(id: String) {
        val client = AsyncHttpClient()
        val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val mealJson = json.jsonObject.getJSONArray("meals").getJSONObject(0)

                    val title = mealJson.getString("strMeal")
                    val image = mealJson.getString("strMealThumb")
                    val instructions = mealJson.getString("strInstructions")
                    val youtubeLink = mealJson.getString("strYoutube")

                    findViewById<TextView>(R.id.text_dish_title).text = title
                    findViewById<TextView>(R.id.text_recipe_instructions).text = instructions

                    Glide.with(this@DishDetailsActivity)
                        .load(image)
                        .into(findViewById<ImageView>(R.id.image_dish))

                    findViewById<Button>(R.id.button_youtube).setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
                    }

                    findViewById<Button>(R.id.button_website).setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://themealdb.com/meal/$id")))
                    }

                } catch (e: Exception) {
                    Log.e("ParseError", "Failed to parse meal JSON", e)
                    Toast.makeText(this@DishDetailsActivity, "Something went wrong parsing meal data.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String, throwable: Throwable?) {
                Log.e("MealDetail", "API Failed: $response")
                Toast.makeText(this@DishDetailsActivity, "Failed to load meal details.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchRandomMeal() {
        val client = AsyncHttpClient()
        val url = "https://www.themealdb.com/api/json/v1/1/random.php"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val mealJson = json.jsonObject.getJSONArray("meals").getJSONObject(0)

                    val title = mealJson.getString("strMeal")
                    val id = mealJson.getString("idMeal")
                    val image = mealJson.getString("strMealThumb")
                    val instructions = mealJson.getString("strInstructions")
                    val youtubeLink = mealJson.getString("strYoutube")

                    findViewById<TextView>(R.id.text_dish_title).text = title
                    findViewById<TextView>(R.id.text_recipe_instructions).text = instructions

                    Glide.with(this@DishDetailsActivity)
                        .load(image)
                        .into(findViewById<ImageView>(R.id.image_dish))

                    findViewById<Button>(R.id.button_youtube).setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
                    }

                    findViewById<Button>(R.id.button_website).setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://themealdb.com/meal/$id")))
                    }

                } catch (e: Exception) {
                    Log.e("ParseError", "Failed to parse meal JSON", e)
                    Toast.makeText(this@DishDetailsActivity, "Something went wrong parsing meal data.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String, throwable: Throwable?) {
                Log.e("MealDetail", "API Failed: $response")
                Toast.makeText(this@DishDetailsActivity, "Failed to load meal details.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
