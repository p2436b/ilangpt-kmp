package tr.com.ilangpt.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import tr.com.ilangpt.chat.domain.models.ChatMessage

class HomeViewModel : ViewModel() {

  val messages = MutableStateFlow<List<ChatMessage>>(emptyList())
  val input = MutableStateFlow("")


  fun updateInput(value: String) {
    input.update { value }
  }

  fun updateMessages(message: ChatMessage) {
    messages.update { it + message }
  }

  fun clearMessages() {
    messages.update { emptyList() }
    input.update { "" }
  }
}