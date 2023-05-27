package com.example.myhomebudgetcompose.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val icon: ImageVector
) {
    object Overview : Screens("overview", Icons.Default.AccountBox)
    object Accounts : Screens("accounts", Icons.Filled.AttachMoney)
    object Bills : Screens("bills", Icons.Filled.MoneyOff)
}