package com.example.composelotto.ui.lottobox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composelotto.ui.theme.LottoBoxColor

@Composable
fun LottoBox(
    selectedItems: List<String>
) {
    fun getItem(index:Int) = selectedItems[index]
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp)
            .background(LottoBoxColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(selectedItems.size){ index ->
                LottoBall(item = getItem(index).toInt())
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun LottoBall(
    item: Int,
) {
    val ballColor = when (item) {
        in 1..10 -> Color.Yellow
        in 11..20 -> Color.Blue
        in 21..31 -> Color.Red
        in 31..40 -> Color.Gray
        else -> Color.Green
    }
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(32.dp)
            .background(ballColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}