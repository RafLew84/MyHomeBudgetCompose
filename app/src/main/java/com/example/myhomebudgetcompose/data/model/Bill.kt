package com.example.myhomebudgetcompose.data.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

data class Bill (
    val name: String,
    val endDate: LocalDate,
    val amount: Double,
    val color: Color
)