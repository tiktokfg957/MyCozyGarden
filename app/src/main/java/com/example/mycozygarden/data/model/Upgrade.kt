package com.example.mycozygarden.data.model

data class Upgrade(
    val id: String,
    val name: String,
    val description: String,
    val basePrice: Int,
    var owned: Boolean = false
)
