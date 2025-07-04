package samaryanin.avitofork.feature.feed.ui.feature.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.feature.feed.ui.feature.card.components.toDisplayString
import samaryanin.avitofork.feature.feed.ui.feature.map.ui.MiniMapView
import samaryanin.avitofork.feature.poster.ui.navigation.PostRoutes
import samaryanin.avitofork.shared.ui.components.RemoteImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInfoScreen(globalNavController: NavHostController) {
    val viewModel: AdditionalInfoViewModel = hiltViewModel()
    val ad by viewModel.adById.collectAsState()
    val lat by viewModel.latitude.collectAsState()
    val lon by viewModel.longitude.collectAsState()
    val fieldMap by viewModel.fieldDefinitionsMap.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(ad?.address) {
        ad?.address?.let { viewModel.loadLatLon(context, it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Объявление") },
                navigationIcon = {
                    IconButton(onClick = { globalNavController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                ad?.let { ad ->
                    RemoteImage(
                        imageId = ad.imageIds.firstOrNull().orEmpty(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(text = ad.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(text = ad.address, fontSize = 14.sp, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    Text(text = "${ad.price} ₽", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(16.dp))
                    Text(text = "Описание", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(4.dp))
                    Text(text = ad.description, fontSize = 14.sp)
                    Spacer(Modifier.height(16.dp))
                    Divider()
                    Spacer(Modifier.height(16.dp))
                    Text(text = "Характеристики", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))

                    ad.fieldValues.forEach { fieldValue ->
                        val fieldName = fieldMap[fieldValue.fieldId]?.name ?: fieldValue.fieldId
                        val valueText = fieldValue.fieldData.toDisplayString()
                        Text(
                            text = "$fieldName: $valueText",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (lat != null && lon != null) {
                                    globalNavController.navigate(PostRoutes.Map.withArgs(lat!!, lon!!))
                                }
                            }
                    ) {
                        Text(
                            text = "Местоположение",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(8.dp))
                        if (lat != null && lon != null) {
                            MiniMapView(latitude = lat!!, longitude = lon!!) {
                                globalNavController.navigate(PostRoutes.Map.withArgs(lat!!, lon!!))
                            }
                        }
                    }
                }
            }
        }
    }
}