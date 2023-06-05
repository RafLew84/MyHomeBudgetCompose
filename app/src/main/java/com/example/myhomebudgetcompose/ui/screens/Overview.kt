package com.example.myhomebudgetcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhomebudgetcompose.data.DataProvider
import com.example.myhomebudgetcompose.data.model.Bill
import com.example.myhomebudgetcompose.ui.components.BaseRow
import com.example.myhomebudgetcompose.util.dateFormatter
import com.example.myhomebudgetcompose.util.formatter
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
private fun CustomAlertDialog(
    onDismiss: () -> Unit,
    buttonText: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = { Text("No Alerts !!!") },
        confirmButton = {
            Column {
                Divider(
                    Modifier.padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                TextButton(
                    onClick = onDismiss,
                    shape = RectangleShape,
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(buttonText)
                }
            }
        }
    )
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

@Composable
private fun <T> OverviewScreenCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card {
        Column {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
                val sign = if (data[0] is Bill) "- " else " "
                val amountText = "$sign${formatter.format(amount)} zł"
                Text(
                    text = amountText,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 8.dp, start = 8.dp)
                        .fillMaxWidth(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            OverViewDivider(data, values, colors)
            LazyColumn(modifier = Modifier.height(200.dp)){
                items(data.size){index ->
                    row(data[index])
                }
            }
            SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll
                )
        }
    }
}

@Composable
private fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(2.dp)
                    .background(colors(item))
            )
        }
    }
}

@Composable
private fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text("SEE ALL")
    }
}