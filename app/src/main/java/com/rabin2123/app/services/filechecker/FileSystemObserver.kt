package com.rabin2123.app.services.filechecker

import android.os.Environment
import android.os.FileObserver
import android.util.Log


class FileSystemObserver(path: String): FileObserver(path,CREATE or MODIFY or DELETE or MOVED_TO or MOVED_FROM or CLOSE_WRITE) {
    override fun onEvent(event: Int, path: String?) {
        Log.d("TAG!", "fileSystem")
        when (event) {
            CREATE -> Log.d("TAG!", "Создан новый файл: $path")
            MODIFY -> Log.d("TAG!", "Файл изменён: $path")
            DELETE -> Log.d("TAG!", "Файл удалён: $path")
            MOVED_TO -> Log.d("TAG!", "Файл перемещён в директорию: $path")
            MOVED_FROM -> Log.d("TAG!", "Файл перемещён из директории: $path")
            CLOSE_WRITE -> Log.d("TAG!", "Загружен: $path")
        }
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
        val PATH: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    }
}