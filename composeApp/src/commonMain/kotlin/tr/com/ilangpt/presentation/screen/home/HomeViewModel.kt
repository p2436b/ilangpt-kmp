package tr.com.ilangpt.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.model.ChatMessage
import tr.com.ilangpt.domain.model.User
import tr.com.ilangpt.domain.repository.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

  val messages = MutableStateFlow<List<ChatMessage>>(emptyList())
  val input = MutableStateFlow("")

  val user = MutableStateFlow<User?>(null)

  init {
    viewModelScope.launch {
      val u = userRepository.upsertUser(
        UserDto(
          provider = 1,
          email = "p2436b@gmail.com"
        )
      )
      val dn = u?.displayName
    }
  }

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