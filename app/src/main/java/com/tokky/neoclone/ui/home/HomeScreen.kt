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
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.tokky.neoclone.data.apk.ApkProcessor
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.rememberCoroutineScope
import com.tokky.neoclone.util.FileUtils
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedFileName by remember { mutableStateOf("ファイルが選択されていません") }
    var isProcessing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val apkProcessor = remember { ApkProcessor(context) }

    val apkFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri: Uri? ->
        uri?.let {
            // ファイル名を表示
            val fileName = FileUtils.getFileName(context, it)
            selectedFileName = "処理中: $fileName"
            isProcessing = true

            // コルーチンでAPKファイルを処理
            coroutineScope.launch {
                apkProcessor.loadApkFromUri(it).fold(
                    onSuccess = { file ->
                        // 成功した場合の処理
                        selectedFileName = "読み込み完了: $fileName (${file.length()} bytes)"
                        isProcessing = false
                    },
                    onFailure = { error ->
                        // エラー時の処理
                        selectedFileName = "エラー: ${error.message}"
                        isProcessing = false
                    }
                )
            }
        }
    }

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
                onClick = { apkFileLauncher.launch(arrayOf("application/vnd.android.package-archive")) },
                icon = { Icon(Icons.Filled.Add, "Select a APK file") },
                text = { Text(text = "Select APK") }
            )
        }
    ) { paddingValues ->
        Column ( modifier = modifier.padding(paddingValues) ) {
            Text("Hello NeoClone!")

            Text(selectedFileName)

            if (isProcessing) {
                CircularProgressIndicator()
            }
        }

    }

}

private fun getFileName(context: Context, uri: Uri): String {
    var result = "unknown"
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                result = cursor.getString(nameIndex)
            }
        }
    }
    return result
}