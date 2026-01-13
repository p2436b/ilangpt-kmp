package tr.com.ilangpt.presentation.screen.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.repository.AuthRepository

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
  val user = authRepository.user

  fun upsertUser(userDto: UserDto) {
    viewModelScope.launch {
      authRepository.upsertUser(userDto)
//        .onSuccess { user -> }
//        .onFailure { error -> }
    }
  }
}