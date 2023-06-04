package com.example.myhomebudgetcompose.data

import androidx.compose.ui.graphics.Color
import com.example.myhomebudgetcompose.data.model.Account
import com.example.myhomebudgetcompose.data.model.Bill
import java.time.LocalDate

object DataProvider {

    val accounts = listOf(
        Account("Home savings", "1111111111", 23456.34, Color.Blue),
        Account("Car savings", "2222222222", 126578.99, Color.LightGray),
        Account("Vacation", "3457733323", 9875.12, Color.Magenta),
        Account("Emergency", "9488344443", 10000.77, Color.Red),
        Account("Healthcare", "3243554434", 12345.00, Color.Yellow),
        Account("Shopping", "2947560007", 3456.56, Color.Black)
    )

    val bills = listOf(
        Bill("Bank Credit", LocalDate.of(2022, 9,22), 2300.0, Color.Black),
        Bill("Tuition", LocalDate.of(2023, 2,10), 1200.0, Color.Blue),
        Bill("Rent", LocalDate.of(2022, 8,3), 1023.87, Color.Yellow),
        Bill("Loan", LocalDate.of(2022, 12,22), 334.0, Color.Gray),
        Bill("Car Repair", LocalDate.of(2023, 1,9), 982.38, Color.White),
        Bill("Dress Loan", LocalDate.of(2023, 5,18), 243.0, Color.Magenta)
    )

    val totalAccountsAmount = (accounts.indices).sumOf { accounts[it].amount }
    val totalBillsAmount = (bills.indices).sumOf { bills[it].amount }
}