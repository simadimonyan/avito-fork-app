package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.feature.poster.domain.models.PostData

@Composable
fun DropdownField(draftOptionsObserver: (PostData) -> Unit, key: String, value: String, options: List<String>, isOnlyOneToChoose: Boolean, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(modifier = Modifier
                .padding(end = 16.dp)
                .width(200.dp), text = key, fontSize = 15.sp, color = Color.Black)

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier
                    .wrapContentWidth(), text = value, fontSize = 15.sp, color = Color.Gray)

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Dropdown arrow",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

        }

    }
}