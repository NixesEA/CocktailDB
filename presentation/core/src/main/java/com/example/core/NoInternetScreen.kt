package com.example.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoInternetScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(50.dp)
                    .aspectRatio(1f / 1f),
                painter = painterResource(id = R.drawable.wifi_error),
                contentDescription = stringResource(R.string.label_no_internet),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = stringResource(R.string.label_no_internet))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NoInternetPreview() {
    NoInternetScreen()
}