package com.example.dairyapp_multi_module_udemy.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onMenuClicked: () -> Unit) {
    TopAppBar(title = { Text(text = "Dairy") }, navigationIcon = {
        IconButton(onClick = onMenuClicked) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon")
        }
    }, actions = {
        IconButton(onClick = onMenuClicked) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Range Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    })
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar({})
}

