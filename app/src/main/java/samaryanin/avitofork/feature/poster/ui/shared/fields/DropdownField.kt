package samaryanin.avitofork.feature.poster.ui.shared.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import samaryanin.avitofork.shared.ui.components.utils.space.Space

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    key: String,
    value: String,
    options: List<String>,
    isOnlyOneToChoose: Boolean,
    isRequired: Boolean,
    isRequiredCheckSubmitted: Boolean,
    showErrorMessage: (String) -> Unit,
    onClick: (String) -> Unit = {},
) {

    var openFlag by remember { mutableStateOf(false) }
    var mutableValue by remember { mutableStateOf(value) }

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(isRequiredCheckSubmitted) {
        if (isRequired && isRequiredCheckSubmitted == true) {
            isError = value.isEmpty() || value == "Не выбрано"
            if (isError) showErrorMessage(key)
        }
    }

    LaunchedEffect(value) {
        mutableValue = value
    }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                openFlag = true
            }
    ) {

        if (openFlag) {
            ModalBottomSheet(
                onDismissRequest = {
                    openFlag = false
                },
                containerColor = Color.White
            ) {
                LazyColumn(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    item {
                        Text(key, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Space()
                    }

                    items(options.size) { index ->

                        val option = options[index]

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isOnlyOneToChoose) {
                                        onClick(option)
                                        mutableValue = option
                                        openFlag = false
                                        isError = false
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(option, fontSize = 20.sp, modifier = Modifier.padding(15.dp))
                        }

                        if (index != options.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                        }
                    }
                }
            }
        }

        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(modifier = Modifier
                .padding(end = 16.dp)
                .width(200.dp), text = key, fontSize = 15.sp, color = if (isError) Color.Red else Color.Black)

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier
                    .wrapContentWidth(), text = if (mutableValue.isEmpty()) "Не выбрано" else mutableValue, fontSize = 15.sp, color = Color.Gray)

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