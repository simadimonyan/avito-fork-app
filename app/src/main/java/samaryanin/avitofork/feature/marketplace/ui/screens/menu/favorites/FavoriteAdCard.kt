package samaryanin.avitofork.feature.marketplace.ui.screens.menu.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.marketplace.domain.model.favorites.Ad
import samaryanin.avitofork.feature.marketplace.ui.screens.menu.search.navigation.SearchRoutes

// Компонент карточки объявления
@Composable
fun FavoriteAdCard(
    ad: Ad,
    isFavorite: Boolean,
    onLikeClick: (ad: Ad) -> Unit,
    globalNavController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                globalNavController.navigate(SearchRoutes.AdditionalInfoScreen.route)
            },
        colors = CardColors(
            Color.Transparent,
            Color.Black,
            Color.Transparent,
            Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Фото объявленияй с
            AsyncImage(
                model = ad.imageUrl, // или URL, если изображение из сети
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Текстовая информация
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Top)
            ) {
                Text(
                    text = ad.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.price,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.address,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Image(
                painter = painterResource(if(isFavorite) R.drawable.like_act else R.drawable.like_non_act),
                contentDescription = "",
                modifier = Modifier
                    .clickable { onLikeClick(ad) }
                    .size(24.dp)
            )
        }
    }
}

