package com.example.budgetlyapp.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.utils.upperFirstChar
import com.example.budgetlyapp.features.home.presentation.components.GraphContainerComponent
import com.example.budgetlyapp.features.home.presentation.components.NextExpenseListComponent
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    val userName by homeViewModel.userName.collectAsState()
    val freeMoneyValue by homeViewModel.freeMoneyValue.collectAsState()
    val pieList by homeViewModel.pieList.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    if (!isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {

            Text(
                text = "${stringResource(R.string.home_title)} ${userName.upperFirstChar()}",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            GraphContainerComponent(pieList, freeMoneyValue) {
                homeViewModel.onClickPie(it)
            }

            NextExpenseListComponent()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}