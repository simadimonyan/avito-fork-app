package samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.product

import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import samaryanin.avitofork.R
import samaryanin.avitofork.data.cache.FavoriteIds.favIdsFlow
import samaryanin.avitofork.domain.model.Ad
import samaryanin.avitofork.presentation.screens.menu.search.navigation.NavigationHolder
import samaryanin.avitofork.presentation.screens.menu.search.navigation.SearchRoutes

@Composable
fun ProductCard(
    ad: Ad,
    isFav: Boolean?,
    globalNavController: NavHostController,
    onFavoriteClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(.7f)
            .clickable {
                NavigationHolder.id = ad.id
                globalNavController.navigate(SearchRoutes.AdditionalInfoScreen.route)
            },
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor =Color.White,
            disabledContentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ad.imageUrl, // или URL, если изображение из сети
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = ad.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = ad.price,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = ad.address,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Image(
                        painter = painterResource(if (isFav == true) R.drawable.like_act else R.drawable.like_non_act),
                        contentDescription = "",
                        modifier = Modifier

                            .clickable {
                                onFavoriteClick()
                                Log.d("FAV", "$favIdsFlow")
                            }
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(R.drawable.more),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                expanded = true
                            }
                            .size(24.dp)
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
                            text = { Text("Действие") },
                            onClick = { }
                        )
                        DropdownMenuItem(
                            text = { Text("Действие") },
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}