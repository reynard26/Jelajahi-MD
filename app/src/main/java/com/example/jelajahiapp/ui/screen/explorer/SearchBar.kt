package com.example.jelajahiapp.ui.screen.explorer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.green87

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    TextField(
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),

        value = query,
        onValueChange = onQueryChange,
        maxLines = 1,
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier
                        .clickable {
                            onQueryChange("")
                        },
                    tint = Color.Black // Set the icon color
                )
            }
            else
            {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black // Set the icon color
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_location),
                color = Color.Black
            )
        },

        modifier = modifier
            .background(color = Color.Transparent)
            .padding(0.dp,5.dp,0.dp,2.dp)
            .fillMaxWidth()
            .heightIn(min = 30.dp)
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .semantics(mergeDescendants = true) {
                contentDescription = "Search Bar"
            }
    )
}