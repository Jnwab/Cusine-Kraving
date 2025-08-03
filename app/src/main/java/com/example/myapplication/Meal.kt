package com.example.myapplication

data class Meal(
    val id: String,
    val name: String,
    val thumbUrl: String,
    val region: String = "",
    val category: String = ""
)
