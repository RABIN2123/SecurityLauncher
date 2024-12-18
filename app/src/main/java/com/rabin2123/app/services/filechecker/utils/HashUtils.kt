package com.rabin2123.app.services.filechecker.utils

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.MessageDigest


object HashUtils {

    private const val STREAM_BUFFER_LENGTH = 1024

    fun getCheckSumFromFile(filePath: String): String {
        val file = File(filePath)
        return getCheckSumFromFile(MessageDigest.getInstance("SHA256"), file)
    }

    private fun getCheckSumFromFile(digest: MessageDigest, file: File): String {
        val fis = FileInputStream(file)
        val byteArray = updateDigest(digest, fis).digest()
        fis.close()
        val hexCode = StringUtils.encodeHex(byteArray, true)
        return String(hexCode)
    }

    /**
     * Reads through an InputStream and updates the digest for the data
     *
     * @param digest The MessageDigest to use (e.g. MD5)
     * @param data Data to digest
     * @return the digest
     */
    private fun updateDigest(digest: MessageDigest, data: InputStream): MessageDigest {
        val buffer = ByteArray(STREAM_BUFFER_LENGTH)
        var read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)
        while (read > -1) {
            digest.update(buffer, 0, read)
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)
        }
        return digest
    }

}