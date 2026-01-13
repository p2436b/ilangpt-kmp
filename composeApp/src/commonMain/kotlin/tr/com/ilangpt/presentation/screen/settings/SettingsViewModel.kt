package tr.com.ilangpt.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tr.com.ilangpt.domain.repository.AuthRepository

class SettingsViewModel(private val authRepository: AuthRepository): ViewModel() {
  fun signOut(){
    viewModelScope.launch {
      authRepository.signOut()
    }
  }
}