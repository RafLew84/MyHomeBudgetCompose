package com.example.myhomebudgetcompose.data.model

import androidx.compose.ui.graphics.Color

data class Account(
    val name: String,
    val number: String,
    val amount: Double,
    val color: Color
)