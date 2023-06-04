package com.example.myhomebudgetcompose.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import com.example.myhomebudgetcompose.data.DataProvider
import com.example.myhomebudgetcompose.data.model.Bill
import com.example.myhomebudgetcompose.ui.components.BaseRow
import com.example.myhomebudgetcompose.ui.components.StatementBody
import com.example.myhomebudgetcompose.ui.theme.MyHomeBudgetComposeTheme
import com.example.myhomebudgetcompose.util.dateFormatter

@Composable
fun BillsScreen(
    bills: List<Bill> = remember { DataProvider.bills }
) {
    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Bills" },
        items = bills,
        amounts = { bill -> bill.amount.toFloat() },
        colors = { bill -> bill.color },
        amountsTotal = bills.map { bill -> bill.amount.toFloat() }.sum(),
        circleLabel = "Total",
        rows = { bill ->
            BaseRow(
                title = bill.name,
                subtitle = bill.endDate.format(dateFormatter),
                amount = bill.amount.toFloat(),
                color = bill.color,
                negative = true
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SecondPreview() {
    MyHomeBudgetComposeTheme {
        BillsScreen()
    }
}