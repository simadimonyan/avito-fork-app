package samaryanin.avitofork.feature.profile.ui.feature.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.favorites.domain.models.Ad
import samaryanin.avitofork.feature.feed.ui.navigation.FeedRoutes
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import samaryanin.avitofork.shared.ui.components.RemoteImage


@Composable
fun UserAdsCard(
    ad: Ad,
    globalNavController: NavHostController,
    onFavoriteClick: () -> Unit,
    isAuthorized: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(.7f)
            .clickable {
                NavigationHolder.id = ad.id
                globalNavController.navigate(FeedRoutes.AdditionalInfoScreen.route)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            RemoteImage(
                imageId = ad.imageIds.firstOrNull().orEmpty(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_pass_view),
                        contentDescription = "views",
                        modifier = Modifier.size(24.dp),
                        alpha = .5f

                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${ad.viewsCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Image(
                    painter = painterResource(R.drawable.more),
                    contentDescription = "more",
                    modifier = Modifier
                        .clickable { expanded = true }
                        .size(24.dp),
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Поделиться") },
                        onClick = { }
                    )
                    DropdownMenuItem(
                        text = { Text("Действие 1") },
                        onClick = { }
                    )
                    DropdownMenuItem(
                        text = { Text("Действие 2") },
                        onClick = { }
                    )
                }
            }
        }
    }
}
