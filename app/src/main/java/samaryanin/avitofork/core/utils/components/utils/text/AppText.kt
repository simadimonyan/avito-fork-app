package samaryanin.avitofork.core.utils.components.utils.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AppTextTitle(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier, fontSize = 24.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun AppTextBody(text: AnnotatedString){
    Text(text, fontSize = 16.sp)
}

@Composable
fun AppTextSmall(text: String, modifier: Modifier?){
    Text(text, fontSize = 12.sp)
}