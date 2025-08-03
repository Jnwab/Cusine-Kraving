package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MealAdapter(private val meals: List<Meal>) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImage: ImageView = itemView.findViewById(R.id.image_dish)
        val dishTitle: TextView = itemView.findViewById(R.id.text_dish_title)
        val dishRegion: TextView = itemView.findViewById(R.id.text_dish_region)
        val dishCategory: TextView = itemView.findViewById(R.id.text_dish_category)
        val buttonMoreInfo: Button = itemView.findViewById(R.id.button_more_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_dish, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]

        holder.dishTitle.text = meal.name
        holder.dishRegion.text = meal.region
        holder.dishCategory.text = meal.category

        Glide.with(holder.itemView.context)
            .load(meal.thumbUrl)
            .into(holder.dishImage)

        holder.buttonMoreInfo.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DishDetailsActivity::class.java)
            intent.putExtra("MEAL_ID", meal.id)



            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = meals.size
}
