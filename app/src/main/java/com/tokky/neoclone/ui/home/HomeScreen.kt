package com.tokky.neoclone.ui.home

import android.view.Surface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSelectApkClicked: () -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text("NeoClone")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onSelectApkClicked() },
                icon = { Icon(Icons.Filled.Add, "Select a APK file") },
                text = { Text(text = "Select APK") }
            )
        }
    ) { paddingValues ->
        Column ( modifier = modifier.padding(paddingValues) ) {
            Text("Hello NeoClone!")
        }

    }

}
