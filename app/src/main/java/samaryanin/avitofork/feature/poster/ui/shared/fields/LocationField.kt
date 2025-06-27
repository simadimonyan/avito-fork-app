package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.components.utils.space.Space

@Composable
fun LocationField(
    key: String,
    value: String,
    determineLocation: () -> Unit,
    isRequiredCheckSubmitted: Boolean,
    isRequired: Boolean,
    showErrorMessage: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                determineLocation()
            }
    ) {

        var isError by remember { mutableStateOf(false) }

        LaunchedEffect(isRequiredCheckSubmitted) {
            if (isRequired && isRequiredCheckSubmitted == true) {
                isError = value.isEmpty() || value == "Не установлено"
                if (isError) showErrorMessage(key)
            }
        }

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = if (isError) Color.Red else Color.DarkGray,
                modifier = Modifier.size(35.dp).align(Alignment.Top)
            )

            Space(7.dp)

            Column {

                Text(modifier = Modifier
                    .padding(end = 16.dp)
                    .width(250.dp), text = key, fontSize = 16.sp, color = if (isError) Color.Red else Color.Black)

                Space(3.dp)

                Text(modifier = Modifier
                    .width(250.dp), text = if (value.isBlank()) "Не установлено" else value, fontSize = 14.sp, color = Color.Gray)

            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Dropdown arrow",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

        }

    }
}