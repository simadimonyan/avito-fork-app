package samaryanin.avitofork.presentation.screens.menu.search.poster_additional_info

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import samaryanin.avitofork.R

@Preview(showSystemUi = false)
@Composable
fun AdditionalInfoPreview() {
    AdditionalInfoContent { true }
}

@Composable
fun AdditionalInfoScreen(
    globalNavController: NavHostController
) {
    val onBack = {
        globalNavController.navigateUp()
    }
    AdditionalInfoContent(onBack)
}

@Composable
fun AdditionalInfoContent(onBack: () -> Boolean) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(32.dp)) {
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
                Image(
                    painter = painterResource(R.drawable.car),
                    // painter = rememberImagePainter(announcement.photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Название товара",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "г. Москва, улица Булгакова 20",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "99999$",
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
                        text = "Описане очень длинное описание Описане очень длинное описание Описане очень длинное описание",
                        fontSize = 14.sp
                    )
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