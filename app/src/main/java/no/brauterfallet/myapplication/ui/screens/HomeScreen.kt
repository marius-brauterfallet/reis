package no.brauterfallet.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.brauterfallet.myapplication.ui.theme.ReisTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel()) {
    Column(modifier = modifier) {
        Text(viewModel.titleText, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ReisTheme {
        HomeScreen()
    }
}