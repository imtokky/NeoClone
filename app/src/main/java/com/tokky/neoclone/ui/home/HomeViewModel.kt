package com.tokky.neoclone.ui.home

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

fun fabFunc() {

}

@Composable
fun rememberApkFilePicker(onApkSelected: (Uri, String) -> Unit): ManagedActivityResultLauncher<Array<String>, Uri?> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            scope.launch(Dispatchers.IO) {
                // ファイル名取得などの処理
                // 実際にはcontentResolverを使用してファイル名を取得する処理を追加
                val fileName = "selected.apk" // 仮実装
                onApkSelected(selectedUri, fileName)
            }
        }
    }

    return launcher
}