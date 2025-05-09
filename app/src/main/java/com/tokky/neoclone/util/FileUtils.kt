package com.tokky.neoclone.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

object FileUtils {

    // uriからファイル名の取得
    fun getFileName(context: Context, uri: Uri): String {
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
}