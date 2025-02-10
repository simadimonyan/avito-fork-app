package samaryanin.avitofork.presentation.screens.poster

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import samaryanin.avitofork.R

@Preview(showSystemUi = false)
@Composable
fun ProductCardScreenPreview() {
    ProductCard(
        Product("мерседес S-19302-s 860", "1200$", "New York", "url"),
    )
}


@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
//        Column(
//            horizontalAlignment = Alignment.Start,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
            Image(
                painter = painterResource(R.drawable.car),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(.8f)
                        .padding(8.dp)
                ) {
                    Text(product.title, style = MaterialTheme.typography.bodyLarge, softWrap = true, maxLines = 1)
                    Text(product.price, style = MaterialTheme.typography.titleLarge, softWrap = true, maxLines = 1)
                    Text(product.location, style = MaterialTheme.typography.bodySmall, softWrap = true, maxLines = 1)
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .weight(.2f)
                        .padding(8.dp)
                ) {
                    Image(
                        modifier = Modifier.
                        clickable {  }
                            .size(24.dp),
                        painter = painterResource(R.drawable.like_non_act),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier.
                        clickable {  }
                            .size(24.dp),
                        painter = painterResource(R.drawable.more),
                        contentDescription = ""
                    )
                }
            }

       // }
    }
}