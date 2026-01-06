package tr.com.ilangpt.chat.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ilangpt.composeapp.generated.resources.Res
import ilangpt.composeapp.generated.resources.search_anything
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ChatInputBar(
  value: String,
  onValueChange: (String) -> Unit,
  onSendClick: () -> Unit
) {
  val focusManager = LocalFocusManager.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    OutlinedTextField(
      modifier = Modifier.weight(1f),
      value = value,
      onValueChange = onValueChange,
      placeholder = { Text(stringResource(Res.string.search_anything)) },
      maxLines = 5,
      shape = RoundedCornerShape(16.dp),
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
      keyboardActions = KeyboardActions(onSend = {
        onSendClick()
        focusManager.clearFocus()
      })
    )
    Spacer(Modifier.width(8.dp))
    Button(
      onClick = {
        onSendClick()
        focusManager.clearFocus()
      },
      enabled = value.trim().isNotEmpty()
    ) {
      Text("Send")
    }
  }
}

