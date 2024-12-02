package com.rabin2123.app.services.filechecker

import android.os.Environment
import android.os.FileObserver
import android.util.Log
import com.rabin2123.app.services.filechecker.utils.HashUtils
import com.rabin2123.domain.repositoryinterfaces.RemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * File observer for control download new file from ethernet
 *
 * @param scanPath path to folder
 */
class FileSystemObserver(scanPath: String) : FileObserver(scanPath, CLOSE_WRITE), KoinComponent {

    private val remoteRepository: RemoteRepository by inject()

    override fun onEvent(event: Int, path: String?) {
        val hash = HashUtils.getCheckSumFromFile("$DOWNLOADS_PATH/$path")
        if (hash == HASH_OF_EMPTY_FILE) return
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        scope.launch {
            Log.d("TAG!", "Result from bazaar: ${remoteRepository.getInfoAboutHashFile(hash)}")
        }
        //TODO сделать отправку компании инфы об файле если он вирус 
    }

    override fun startWatching() {
        Log.d("TAG!", "StartWatching")
        super.startWatching()
    }

    override fun stopWatching() {
        Log.d("TAG!", "StopWatching")
        super.stopWatching()
    }

    companion object {
        val DOWNLOADS_PATH: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        const val HASH_OF_EMPTY_FILE: String =
            "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
    }
}