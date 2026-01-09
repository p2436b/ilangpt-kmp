package tr.com.ilangpt.presentation.screen.terms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TermsOfUseScreen() {
  Scaffold { padding ->
    Surface(Modifier.fillMaxSize().padding(padding)) {
      Text("Terms of use", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
  }
}