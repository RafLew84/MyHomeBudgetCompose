package com.example.myhomebudgetcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myhomebudgetcompose.data.DataProvider
import com.example.myhomebudgetcompose.ui.components.BaseRow
import com.example.myhomebudgetcompose.ui.components.StatementBody
import com.example.myhomebudgetcompose.ui.theme.MyHomeBudgetComposeTheme

@Composable
fun AccountsScreen() {
    val amountsTotal = remember { DataProvider.accounts.sumOf { account -> account.amount }.toFloat() }
    StatementBody(
        modifier = Modifier.semantics { contentDescription = "Accounts Screen" },
        items = DataProvider.accounts,
        amounts = { account -> account.amount.toFloat() },
        colors = { account -> account.color },
        amountsTotal = amountsTotal,
        circleLabel = "Total",
        rows = { account ->
            BaseRow(
                title = account.name,
                subtitle = account.number.replaceRange(0 until 6, "******"),
                amount = account.amount.toFloat(),
                color = account.color,
                negative = false
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FirstPreview() {
    MyHomeBudgetComposeTheme {
        AccountsScreen()
    }
}