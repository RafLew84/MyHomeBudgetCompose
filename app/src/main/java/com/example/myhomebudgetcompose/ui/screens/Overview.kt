package com.example.myhomebudgetcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhomebudgetcompose.data.DataProvider
import com.example.myhomebudgetcompose.ui.components.BaseRow
import com.example.myhomebudgetcompose.ui.components.CustomAlertDialog
import com.example.myhomebudgetcompose.ui.components.OverviewScreenCard
import com.example.myhomebudgetcompose.util.dateFormatter
import java.util.Locale

@Composable
fun OverviewScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        AlertCard()
        Spacer(Modifier.height(12.dp))
        AccountsCard(onClickSeeAll = onClickSeeAllAccounts)
        Spacer(Modifier.height(12.dp))
        BillsCard(onClickSeeAll = onClickSeeAllBills)
    }
}

@Composable
private fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        CustomAlertDialog(
            onDismiss = { showDialog = false },
            buttonText = "Dismiss".uppercase(Locale.getDefault())
        )
    }
    Card {
        Column {
            Alert {
                showDialog = true
            }
        }
    }
}

@Composable
private fun Alert(onClickSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier.fillMaxHeight()
        ){
            Text(
                text = "Alerts",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "No Alerts !!!",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 12.dp, top = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onClickSeeAll,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 8.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(
                text = "SEE ALL"
            )
        }
    }
}

@Composable
private fun AccountsCard(onClickSeeAll: () -> Unit) {
    val amount = DataProvider.accounts.sumOf { account -> account.amount }
    OverviewScreenCard(
        title = "Accounts",
        amount = amount.toFloat(),
        onClickSeeAll = onClickSeeAll,
        data = DataProvider.accounts,
        colors = { it.color },
        values = { it.amount.toFloat() }
    ) { account ->
        BaseRow(
            title = account.name,
            amount = account.amount.toFloat(),
            color = account.color,
            negative = false,
            subtitle = account.number.replaceRange(0 until 6, "******")
        )
    }
}

@Composable
private fun BillsCard(onClickSeeAll: () -> Unit) {
    val amount = DataProvider.bills.sumOf { bill -> bill.amount }
    OverviewScreenCard(
        title = "Bills",
        amount = amount.toFloat(),
        onClickSeeAll = onClickSeeAll,
        data = DataProvider.bills,
        colors = { it.color },
        values = { it.amount.toFloat() }
    ) { bill ->
        BaseRow(
            color = bill.color,
            title = bill.name,
            subtitle = bill.endDate.toString().format(dateFormatter),
            amount = bill.amount.toFloat(),
            negative = true
        )
    }
}

