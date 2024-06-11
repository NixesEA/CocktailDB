package com.example.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.CocktailDataUI

@Composable
fun Content(
    isLoading: Boolean,
    isNetworkError: Boolean,
    data: CocktailDataUI?,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Loader()
    }
    if (isNetworkError) {
        NoInternetScreen(modifier = modifier)
    } else if (data != null) {
        CocktailDetails(modifier = modifier, data = data)
    }
}

@Composable
fun CocktailDetails(modifier: Modifier = Modifier, data: CocktailDataUI) {
    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(bottomEndPercent = 20, bottomStartPercent = 20)
                )
                .clip(RoundedCornerShape(bottomEndPercent = 20, bottomStartPercent = 20)),
            model = data.imageUrl,
            contentScale = ContentScale.FillWidth,
            contentDescription = "Image of ${data.name}",
        )
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = data.name,
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "#${data.idDrink}",
                    color = Color.LightGray
                )
            }
            Text(text = "${data.alcoholic} ${data.category}", color = Color.Gray)
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = data.instructions)
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.ingredients_label))
            data.ingredients.forEach {
                Text(
                    text = it,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    Content(
        isLoading = false,
        isNetworkError = false,
        data = CocktailDataUI.mock(),
    )
}

@Preview(showSystemUi = true)
@Composable
private fun DetailsScreenNoInternetPreview() {
    Content(
        isLoading = false,
        isNetworkError = true,
        data = CocktailDataUI.mock(),
    )
}