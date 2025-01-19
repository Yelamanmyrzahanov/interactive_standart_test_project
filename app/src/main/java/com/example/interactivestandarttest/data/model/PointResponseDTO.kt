package com.example.interactivestandarttest.data.model

import com.google.gson.annotations.SerializedName

data class PointResponseDTO(
    @SerializedName("points")
    val points: List<PointDTO>
)