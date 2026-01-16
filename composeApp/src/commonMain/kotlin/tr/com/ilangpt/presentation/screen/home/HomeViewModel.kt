package tr.com.ilangpt.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tr.com.ilangpt.domain.model.ChatMessage
import tr.com.ilangpt.domain.repository.AuthRepository
import tr.com.ilangpt.domain.repository.ListingRepository

class HomeViewModel(
  authRepository: AuthRepository,
  private val listingRepository: ListingRepository
) : ViewModel() {
  val user = authRepository.user
  val messages = MutableStateFlow<List<ChatMessage>>(emptyList())
  private val _listingHistory = MutableStateFlow<List<ChatMessage>>(
    listOf(
      ChatMessage(
        title = "Modern Apartment for Rent", isMine = true
      ), ChatMessage(
        title = "Cozy Studio in City Center", isMine = false
      ), ChatMessage(
        title = "Spacious Family House", isMine = true
      ), ChatMessage(
        title = "Luxury Condo with Sea View", isMine = false
      ), ChatMessage(
        title = "Affordable Room Near University", isMine = false
      )
    )
  )
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery

  private val _historyQuery = MutableStateFlow("")
  val historyQuery: StateFlow<String> = _historyQuery

  val filteredListingHistory: StateFlow<List<ChatMessage>> =
    combine(_listingHistory, historyQuery) { history, query ->
      if (query.isBlank()) {
        history
      } else {
        history.filter {
          it.title.contains(query, ignoreCase = true)
        }
      }
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = emptyList()
    )

  fun queryHistory(query: String) {
    _historyQuery.update { query }
  }

  fun updateSearchQuery(query: String) {
    _searchQuery.update { query }
  }

  fun queryListing(query: String) {
    viewModelScope.launch {

      listingRepository.getListings(query)
        .onSuccess { listings ->
          listings.forEach { listing ->
            messages.update {
              it + ChatMessage(
                id = listing.id.toString(),
                title = listing.title,
                url = listing.url,
                coverImage = listing.coverImage,
                websiteName = listing.websiteName,
                city = listing.city,
                district = listing.district,
                neighborhood = listing.neighborhood,
                listingType = listing.listingType,
                categoryPath = listing.categoryPath,
                score = listing.score,
                isMine = false
              )
            }
          }
        }
    }
  }

  fun updateMessages(message: ChatMessage) {
    messages.update { it + message }
  }

  fun clearMessages() {
    messages.update { emptyList() }
    _searchQuery.update { "" }
  }

  fun onSendQuery() {
    val trimmed = searchQuery.value.trim()
    if (trimmed.isEmpty()) return

    updateMessages(ChatMessage(title = trimmed, isMine = true))
    queryListing(trimmed)
    updateSearchQuery("")

  }
}