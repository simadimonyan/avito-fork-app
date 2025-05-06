package samaryanin.avitofork.feature.favorites.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.shared.ui.components.RemoteImage
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import samaryanin.avitofork.feature.feed.ui.navigation.FeedRoutes

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
                globalNavController.navigate(FeedRoutes.AdditionalInfoScreen.route)
                NavigationHolder.id = ad.id
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Загрузка изображения с shimmer-анимацией
            RemoteImage(
                imageId = ad.imageIds.firstOrNull().orEmpty(),
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
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
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.price,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.address,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(if (isFavorite) R.drawable.like_act else R.drawable.like_non_act),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onLikeClick(ad) }
                    .align(Alignment.Top)
            )
        }
    }
}