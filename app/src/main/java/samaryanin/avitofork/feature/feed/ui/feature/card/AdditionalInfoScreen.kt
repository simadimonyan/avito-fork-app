package samaryanin.avitofork.feature.feed.ui.feature.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import samaryanin.avitofork.R
import samaryanin.avitofork.feature.feed.ui.feature.map.ui.MiniMapView
import samaryanin.avitofork.feature.feed.ui.navigation.NavigationHolder
import samaryanin.avitofork.feature.poster.ui.navigation.PostRoutes
import samaryanin.avitofork.shared.ui.components.RemoteImage
import samaryanin.avitofork.shared.ui.components.utils.text.AppTextTitle

@Composable
fun AdditionalInfoScreen(
    globalNavController: NavHostController
) {
    val onBack = {
        globalNavController.navigateUp()
    }


    AdditionalInfoContent(onBack, globalNavController)
}

@Composable
fun AdditionalInfoContent(
    onBack: () -> Boolean,
    globalNavController: NavHostController

    // ad: Ad?
) {
    val context = LocalContext.current
    val viewModel: AdditionalInfoViewModel = hiltViewModel()
    SideEffect {
        NavigationHolder.id?.let { viewModel.getAdById(it) }
    }
    val ad by viewModel.adById.collectAsState()

    val lat by viewModel.latitude.collectAsState()
    val lon by viewModel.longitude.collectAsState()

    LaunchedEffect(ad?.address) {
        ad?.address?.let { viewModel.loadLatLon(context, it) }
    }
    val map = {
        if (lat != null && lon != null) {
            globalNavController.navigate(PostRoutes.Map.withArgs(lat!!, lon!!))
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow),
                    contentDescription = null,
                    modifier = Modifier.clickable { onBack() })
            }
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column {
                    if (ad != null) {
                        RemoteImage(
                            imageId = ad!!.imageIds.firstOrNull().orEmpty(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(400.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = ad?.title ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = ad?.address ?: "",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = ad?.price ?: "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Описание", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = ad?.description ?: "",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                        Column(
                            modifier = Modifier.clickable {
                                map()
                            }
                        ) {

                            AppTextTitle(
                                text = "Местоположение",
                                modifier = Modifier.clickable {
                                    map()
                                })
                            if (lat != null && lon != null) {
                                MiniMapView(latitude = lat!!, longitude = lon!!, onClick = map)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.car),
                        //painter = rememberImagePainter(announcement.owner.avatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Владелец",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Иван",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { /* TODO: Contact Owner */ }) {
                        Text(text = "Связаться")
                    }
                }
            }
        }
    }
}

//@Preview(showSystemUi = false)
//@Composable
//fun AdditionalInfoPreview() {
//    AdditionalInfoContent({ true }, viewModel, ad)
//}
