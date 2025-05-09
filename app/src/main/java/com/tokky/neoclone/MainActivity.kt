package com.tokky.neoclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tokky.neoclone.ui.home.HomeScreen
import com.tokky.neoclone.ui.home.rememberApkFilePicker
import com.tokky.neoclone.ui.theme.NeoCloneTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NeoCloneTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val apkPicker = rememberApkFilePicker { uri, fileName ->
        // APKファイルが選択された時の処理
        coroutineScope.launch(Dispatchers.IO) {
            // TODO:ファイル処理の実装
        }
    }

    Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
        HomeScreen(
            modifier = Modifier.padding(innerPadding),
            onSelectApkClicked = {
                apkPicker.launch(arrayOf("application/vnd.android.package-archive"))
            }
        )
    }

}