package com.example.myhomebudgetcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhomebudgetcompose.util.formatter

@Composable
fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    subtitle: String,
    amount: Float,
    negative: Boolean
) {
    val sign = if (negative) "– " else " "
    val formattedAmount = ("$sign${formatter.format(amount)} zł")
    Row(
        modifier = modifier.height(68.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier.size(4.dp, 36.dp).background(color = color))
        Spacer(Modifier.width(12.dp))
        Column(Modifier) {
            Text(text = title)
            Text(text = subtitle)
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = formattedAmount,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}