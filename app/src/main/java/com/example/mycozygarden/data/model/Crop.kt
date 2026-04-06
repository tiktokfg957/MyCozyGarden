package com.example.mycozygarden.data.model

enum class CropType {
    POTATO, TOMATO, SUNFLOWER, STRAWBERRY, PUMPKIN, CORN, PEPPER
}

data class Crop(
    val type: CropType,
    val name: String,
    val priceToPlant: Int,
    val baseHarvestValue: Int,
    val baseGrowthTimeSec: Int
) {
    companion object {
        val all = listOf(
            Crop(CropType.POTATO, "Картофель", 10, 30, 100),
            Crop(CropType.TOMATO, "Помидор", 20, 60, 120),
            Crop(CropType.SUNFLOWER, "Подсолнух", 50, 150, 180),
            Crop(CropType.STRAWBERRY, "Клубника", 100, 300, 240),
            Crop(CropType.PUMPKIN, "Тыква", 300, 900, 360),
            Crop(CropType.CORN, "Кукуруза", 80, 240, 200),
            Crop(CropType.PEPPER, "Перец", 120, 360, 220)
        )
        fun getByType(type: CropType) = all.find { it.type == type }!!
    }
}
