package com.example.interactivestandarttest.data.model

import com.example.interactivestandarttest.domain.model.Point
import com.google.gson.annotations.SerializedName


data class PointDTO(
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float,
) {
    // Расширение для преобразования PointDto -> Point
    fun toDomainModel(): Point {
        return Point(
            x = this.x,
            y = this.y
        )
    }

}