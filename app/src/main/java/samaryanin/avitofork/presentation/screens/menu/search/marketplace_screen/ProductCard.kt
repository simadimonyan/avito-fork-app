package samaryanin.avitofork.presentation.screens.menu.search

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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.data.database.favorites.Favorite
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.MarketplaceViewModel
import samaryanin.avitofork.presentation.screens.menu.search.marketplace_screen.Product
import samaryanin.avitofork.presentation.screens.menu.search.navigation.SearchRoutes

//@Preview(showSystemUi = false)
//@Composable
//fun ProductCardScreenPreview() {
//    ProductCard(
//        Product("мерседес S-19302-s 860", "1200$", "New York", "url"),
//        globalNavController,
//    )
//}


@Composable
fun ProductCard(product: Product, globalNavController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    var isFav by remember { mutableStateOf(false) }

    val viewModel: MarketplaceViewModel = hiltViewModel()
    val favorites by viewModel.allFavorites.collectAsState()

    if(favorites.contains(Favorite(product.id))) isFav = true else isFav = false

    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable {
                globalNavController.navigate(SearchRoutes.AdditionalInfoScreen.route)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.car),
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
                        text = product.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = product.location,
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
                        painter = painterResource(
                            if (isFav) R.drawable.like_act else
                                R.drawable.like_non_act
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                isFav = !isFav
                                if(isFav) viewModel.addToFavorites(product.id)
                                else viewModel.removeFromFavorites(product.id)
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