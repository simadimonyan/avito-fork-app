package samaryanin.avitofork.shared.ui.components.placeholders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.R
import samaryanin.avitofork.shared.ui.components.utils.space.Space
import samaryanin.avitofork.shared.ui.theme.lightGrayColor

@Preview
@Composable
fun ProfileEmptyPublication() {
    Card(
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(7.dp),
        border = BorderStroke(0.dp, lightGrayColor),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(lightGrayColor)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(7.dp)
                        .fillMaxWidth(0.6f)
                        .background(lightGrayColor, shape = RoundedCornerShape(2.dp))
                )
                Space(2.dp)
                Box(
                    modifier = Modifier
                        .height(7.dp)
                        .fillMaxWidth(0.8f)
                        .background(lightGrayColor, shape = RoundedCornerShape(2.dp))
                )
                Space(2.dp)
                Box(
                    modifier = Modifier
                        .height(7.dp)
                        .fillMaxWidth(0.4f)
                        .background(lightGrayColor, shape = RoundedCornerShape(2.dp))
                )
                Space(2.dp)
                Box(
                    modifier = Modifier
                        .height(7.dp)
                        .fillMaxWidth(0.6f)
                        .background(lightGrayColor, shape = RoundedCornerShape(2.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePublicationPreview() {
    ProfilePublication("Легковой автомобиль", "Очень крутой", "200 000 руб")
}

@Composable
fun ProfilePublication(title: String, location: String, price: String) {

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(7.dp),
        border = BorderStroke(0.dp, Color.Transparent),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White),
        ) {
            Image(
                painter = painterResource(R.drawable.house),
                contentDescription = "Фото объявления",
                modifier = Modifier
                    .size(190.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(
                    text = price,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = location,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Space(5.dp)
            }
        }
    }
}
