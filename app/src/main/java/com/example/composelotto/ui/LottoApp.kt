package com.example.composelotto.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composelotto.R
import com.example.composelotto.ui.lottobox.LottoBox
import com.example.composelotto.ui.picker.Picker
import com.example.composelotto.ui.theme.HoloBlueDark
import kotlinx.coroutines.launch

@Composable
fun LottoApp(
    viewModel: LottoViewModel = viewModel()
) {

    val items by viewModel.items.collectAsState(lottoList)
    val selectedItems by viewModel.selectedItems.collectAsState()

    val errorSnackBar = remember { SnackbarHostState() }

    Coordinate(snackbar = errorSnackBar) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.size(32.dp))

            Image(
                painter = painterResource(id = R.drawable.lotto), contentDescription = "",
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "로또 번호 생성기",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = HoloBlueDark,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.size(16.dp))

            if (items.isNotEmpty()) {
                LottoRow(items) { item ->
                    viewModel.selectedItems(item)
                }
            }
            
            Spacer(modifier = Modifier.size(16.dp))

            LottoBox(selectedItems)
            Spacer(modifier = Modifier.weight(1f))
            Row(
            ) {
                Button(onClick = { viewModel.autoGenerate() },
                    modifier = Modifier
                        .weight(3f)
                        .padding(8.dp)) {
                    Text(text = "자동생성 시작")
                }
                Button(onClick = { viewModel.resetItems() },
                    modifier = Modifier
                        .weight(2f)
                        .padding(8.dp)) {
                    Text(text = "초기화")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Coordinate(
    viewModel: LottoViewModel = viewModel(),
    snackbar: SnackbarHostState,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarScope = rememberCoroutineScope()
    Scaffold(
        topBar = {TopAppBar(title = { Text(text = "로또 번호 추첨기") })},
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) {
        content(it)

        LaunchedEffect(key1 = viewModel.duplicateEvent) {
            viewModel.duplicateEvent.collect { message ->
                snackbarScope.launch {
                    snackbar.showSnackbar(message = message)
                }
            }
        }
    }
}

@Composable
fun LottoRow(
    items: List<String>,
    onClickButton: (String) -> Unit
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Picker(
            items = items,
            modifier = Modifier
                .width(width = 100.dp)
        ){ selectedItem ->
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = { onClickButton(selectedItem) },
                modifier = Modifier.align(Alignment.CenterVertically,
                )) {
                Text(text = "번호 추가 하기")
            }
        }
    }
}