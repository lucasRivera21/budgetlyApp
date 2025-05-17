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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.present.register.components.AboutYouScreen
import com.example.budgetlyapp.present.register.components.IncomingInfoScreen
import com.example.budgetlyapp.present.register.components.PagerIndicator
import com.example.budgetlyapp.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen() {
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 }
    )

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Header()

        Spacer(Modifier.size(48.dp))

        ViewPager(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            pagerState
        )

        Button(onClick = {
            scope.launch {
                if (pagerState.pageCount != pagerState.currentPage + 1) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.register_next_button))
        }
    }
}

@Composable
private fun Header() {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "arrow back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            "Registrarse",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ViewPager(modifier: Modifier, pagerState: PagerState) {

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> AboutYouScreen()
                1 -> IncomingInfoScreen()
                2 -> PersonalInfoScreen()
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

@Composable
private fun PersonalInfoScreen() {
    Text("Personal info")
}

@Preview(showSystemUi = true, showBackground = true, apiLevel = 34)
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen()
    }
}