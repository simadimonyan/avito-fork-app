package samaryanin.avitofork.feature.favorites.ui.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.ui.navigation.FeedRoutes
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import samaryanin.avitofork.shared.extensions.formatPrice
import samaryanin.avitofork.shared.ui.components.RemoteImage

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
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable {
                NavigationHolder.id = ad.id
                globalNavController.navigate(FeedRoutes.AdditionalInfoScreen.route)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Фото
            RemoteImage(
                imageId = ad.imageIds.firstOrNull().orEmpty(),
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Основная информация
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = ad.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = ad.price.formatPrice(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
               //     color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = ad.address,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Иконка избранного
            Icon(
                painter = painterResource(id = R.drawable.like_act),
                contentDescription = null,
                tint = Color.Unspecified, // <-- добавь это
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onLikeClick(ad) }
                    .align(Alignment.Top)
            )
        }
    }
}