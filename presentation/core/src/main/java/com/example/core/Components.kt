package com.example.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Loader(modifier: Modifier = Modifier, isVisible: Boolean = true) {
    if (isVisible) {
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoaderPreview(modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    )
}

@Composable
fun Button(label: String, action: () -> Unit) {
    TextButton(
        modifier = Modifier
            .background(color = Color(color = 0xffffe5b4), shape = RoundedCornerShape(percent = 20))
            .padding(all = 5.dp),
        onClick = action
    ) {
        Text(
            text = label,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    Button(label = "Some Text") {}
}