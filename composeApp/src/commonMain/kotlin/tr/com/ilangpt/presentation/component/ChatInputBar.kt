package tr.com.ilangpt.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ChatInputBar(
  modifier: Modifier ,
  value: String,
  onValueChange: (String) -> Unit,
  onSendClick: () -> Unit
) {
  val focusManager = LocalFocusManager.current

  TextFieldWithTrailingButton(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    onButtonClick = {
      focusManager.clearFocus()
      onSendClick()
    }
  )
}

@Composable
fun TextFieldWithTrailingButton(
  value: String,
  onValueChange: (String) -> Unit,
  onButtonClick: () -> Unit,
  modifier: Modifier
) {
  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    singleLine = true,
    textStyle = TextStyle(
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onBackground
    ),
    decorationBox = { innerTextField ->
      Column(modifier.height(72.dp), verticalArrangement = Arrangement.Top) {
        Row(
          Modifier.background(
            MaterialTheme.colorScheme.background,
            RoundedCornerShape(8.dp)
          ).padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
          Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
              Text(
                text = "Type something…",
                color = Color.Gray
              )
            }
            innerTextField()
          }
          Spacer(modifier = Modifier.width(8.dp))
          if (value.isEmpty()) {
            IconButton(onClick = onButtonClick) {
              Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Clear"
              )
            }
          }
          IconButton(
            enabled = value.trim().isNotEmpty(),
            onClick = onButtonClick
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Default.Send,
              contentDescription = "Send"
            )
          }
        }
      }
    }
  )
}


