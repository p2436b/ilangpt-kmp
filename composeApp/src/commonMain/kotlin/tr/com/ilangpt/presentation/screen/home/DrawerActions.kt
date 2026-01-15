package tr.com.ilangpt.presentation.screen.home

import tr.com.ilangpt.domain.model.ChatMessage
import androidx.compose.runtime.State

interface DrawerActions {
  val historyQuery: State<String>
  val filteredListingHistory: State<List<ChatMessage>>
  fun queryHistory(query: String)
  fun onHistoryItemClick(item: ChatMessage)
}
