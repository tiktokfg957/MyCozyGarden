package com.example.mycozygarden.data.model

enum class CropType {
    POTATO, TOMATO, SUNFLOWER, STRAWBERRY, PUMPKIN, CORN, PEPPER
}

data class Crop(
    val type: CropType,
    val nameRes: Int,
    val priceToPlant: Int,
    val baseHarvestValue: Int,
    val baseGrowthTimeSec: Int
) {
    companion object {
        val all = listOf(
            Crop(CropType.POTATO, R.string.potato, 10, 30, 100),
            Crop(CropType.TOMATO, R.string.tomato, 20, 60, 120),
            Crop(CropType.SUNFLOWER, R.string.sunflower, 50, 150, 180),
            Crop(CropType.STRAWBERRY, R.string.strawberry, 100, 300, 240),
            Crop(CropType.PUMPKIN, R.string.pumpkin, 300, 900, 360),
            Crop(CropType.CORN, R.string.corn, 80, 240, 200),
            Crop(CropType.PEPPER, R.string.pepper, 120, 360, 220)
        )
        fun getByType(type: CropType) = all.find { it.type == type }!!
    }
}
