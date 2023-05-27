package com.example.myhomebudgetkotlin.data.model

import java.time.LocalDate

data class Bill (
    val name: String,
    val endDate: LocalDate,
    val amount: Double,
    val color: Int
)