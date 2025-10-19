package com.lucasdev.budgetlyapp.features.home.presentation

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lucasdev.budgetlyapp.R
import com.lucasdev.budgetlyapp.common.utils.hasNotificationPermission
import com.lucasdev.budgetlyapp.common.utils.upperFirstChar
import com.lucasdev.budgetlyapp.features.home.presentation.components.ExpenseHomeBox
import com.lucasdev.budgetlyapp.features.home.presentation.components.GraphContainerComponent
import com.lucasdev.budgetlyapp.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("InlinedApi")
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val userName by homeViewModel.userName.collectAsState()
    val freeMoneyValue by homeViewModel.freeMoneyValue.collectAsState()
    val pieList by homeViewModel.pieList.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val nextTaskList by homeViewModel.nextTaskList.collectAsState()
    val isFirstTime by homeViewModel.isFirstTime.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            homeViewModel.putFirstTimeFalse()
        }

    LaunchedEffect(isFirstTime) {
        if (isFirstTime) {
            if (!hasNotificationPermission(context)) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if (!isLoading) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "${stringResource(R.string.home_title)} ${userName.upperFirstChar()}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                )
            }

            item {
                GraphContainerComponent(
                    pieList,
                    freeMoneyValue,
                    Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    homeViewModel.onClickPie(it)
                }
            }

            if (nextTaskList.isNotEmpty()) {
                item {
                    Text(
                        stringResource(R.string.home_next_expense_title),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                nextTaskList.forEach { initial, taskList ->
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            HorizontalDivider()
                            Text(
                                initial,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }

                    items(taskList) { nextTaskModel ->
                        key(nextTaskModel.taskId) {
                            var isVisible by remember(nextTaskModel.taskId) { mutableStateOf(true) }
                            AnimatedVisibility(visible = isVisible, exit = shrinkVertically()) {
                                ExpenseHomeBox(
                                    nextTaskModel,
                                    Modifier.padding(horizontal = 16.dp)
                                ) {
                                    coroutineScope.launch {
                                        isVisible = false
                                        delay(250)
                                        homeViewModel.updateIsCompleteTask(it)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_relaxation),
                                contentDescription = null
                            )

                            Text(
                                stringResource(R.string.home_without_next_expenses),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
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