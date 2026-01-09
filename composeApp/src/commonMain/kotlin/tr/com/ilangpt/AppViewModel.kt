package tr.com.ilangpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import tr.com.ilangpt.domain.model.AuthState
import tr.com.ilangpt.domain.repository.PreferencesRepository

class AppViewModel(preferencesRepository: PreferencesRepository) : ViewModel() {

  val authState = preferencesRepository.authState
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = AuthState.Loading
    )
}
