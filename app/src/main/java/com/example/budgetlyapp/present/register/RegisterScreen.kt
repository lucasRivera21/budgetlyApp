package com.example.budgetlyapp.present.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.present.register.components.AboutYouScreen
import com.example.budgetlyapp.present.register.components.AccountInfoScreen
import com.example.budgetlyapp.present.register.components.IncomingInfoScreen
import com.example.budgetlyapp.present.register.components.PagerIndicator
import com.example.budgetlyapp.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(
        pageCount = { 3 }
    )
    val scope = rememberCoroutineScope()

    //About You
    val name by registerViewModel.name.collectAsState()
    val lastName by registerViewModel.lastName.collectAsState()
    val dayBirth by registerViewModel.dayBirth.collectAsState()
    val monthBirth by registerViewModel.monthBirth.collectAsState()
    val yearBirth by registerViewModel.yearBirth.collectAsState()

    //Incoming Info
    val incomeValue by registerViewModel.incomeValue.collectAsState()
    val moneyType by registerViewModel.moneyType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Header {
            scope.launch {
                if (pagerState.currentPage != 0) {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                } else {
                    navController.popBackStack()
                }
            }
        }

        Spacer(Modifier.size(48.dp))

        ViewPager(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            pagerState,
            name,
            lastName,
            dayBirth,
            monthBirth,
            yearBirth,
            incomeValue,
            moneyType,
            registerViewModel
        )

        Button(onClick = {
            registerViewModel.validateForm(pagerState.currentPage) {
                scope.launch {
                    if (pagerState.pageCount != pagerState.currentPage + 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.register_next_button))
        }
    }
}

@Composable
private fun Header(onChangePage: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            onChangePage()
        }, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "arrow back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            stringResource(R.string.register_title),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ViewPager(
    modifier: Modifier,
    pagerState: PagerState,
    name: String,
    lastName: String,
    dayBirth: String,
    monthBirth: String,
    yearBirth: String,
    incomeValue: String,
    moneyType: String,
    registerViewModel: RegisterViewModel
) {

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> AboutYouScreen(
                    name,
                    lastName,
                    dayBirth,
                    monthBirth,
                    yearBirth,
                    registerViewModel
                )

                1 -> IncomingInfoScreen(incomeValue, moneyType, registerViewModel)
                2 -> AccountInfoScreen()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            PagerIndicator(pagerState.pageCount, pagerState.currentPage)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, apiLevel = 34)
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(NavController(LocalContext.current))
    }
}