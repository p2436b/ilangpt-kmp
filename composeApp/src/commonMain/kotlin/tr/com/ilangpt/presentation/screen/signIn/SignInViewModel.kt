package tr.com.ilangpt.presentation.screen.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.model.User
import tr.com.ilangpt.domain.repository.PreferencesRepository
import tr.com.ilangpt.domain.repository.UserRepository

class SignInViewModel(
  private val userRepository: UserRepository,
  private val prefs: PreferencesRepository
) : ViewModel() {
  private val _user = MutableStateFlow<User?>(null)
  val user: StateFlow<User?> = _user

  fun upsertUser(userDto: UserDto) {
    viewModelScope.launch {
      val u: User? = userRepository.upsertUser(userDto)
      _user.value = u
    }
  }
}