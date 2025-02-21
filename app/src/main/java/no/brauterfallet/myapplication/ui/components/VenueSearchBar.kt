package no.brauterfallet.myapplication.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueSearchBar(
    searchBarText: String,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchBarText,
                onQueryChange = onQueryChange,
                onSearch = { println("Searching for '$searchBarText'") },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = Icons.Default.Search.name)
                }
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.fillMaxWidth(),
        content = content
    )
}