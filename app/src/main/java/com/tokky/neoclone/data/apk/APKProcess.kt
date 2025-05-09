package com.tokky.neoclone.data.apk

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ApkProcessor(private val context: Context) {

    private val apkTAG = "ApkProcessor"

    /**
     * URIからAPKファイルを読み込み、一時ファイルに保存する
     * 現段階では単にファイルをコピーするだけ
     */
    suspend fun loadApkFromUri(uri: Uri): Result<File> = withContext(Dispatchers.IO) {
        try {
            // 一時ファイル名を生成
            val tempFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}.apk")

            // ファイルをコピー
            context.contentResolver.openInputStream(uri)?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            } ?: return@withContext Result.failure(IllegalStateException("ファイルを開けませんでした"))

            Log.d(apkTAG, "APKファイルをコピーしました: ${tempFile.absolutePath} (${tempFile.length()} bytes)")

            Result.success(tempFile)
        } catch (e: Exception) {
            Log.e(apkTAG, "APKファイル読み込みエラー", e)
            Result.failure(e)
        }
    }
}