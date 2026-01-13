package tr.com.ilangpt.presentation.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import tr.com.ilangpt.domain.model.ChatMessage
import tr.com.ilangpt.domain.repository.AuthRepository

class HomeViewModel(authRepository: AuthRepository) : ViewModel() {

  val user = authRepository.user
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