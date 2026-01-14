package tr.com.ilangpt.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tr.com.ilangpt.domain.repository.AuthRepository

class ProfileViewModel(val authRepository: AuthRepository) : ViewModel() {
  fun signOut() {
    viewModelScope.launch {
      authRepository.signOut()
    }
  }

  val user = authRepository.user
}