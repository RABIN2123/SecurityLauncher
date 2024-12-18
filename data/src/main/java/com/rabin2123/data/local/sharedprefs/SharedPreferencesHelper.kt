package com.rabin2123.data.local.sharedprefs

import android.content.SharedPreferences
import com.rabin2123.data.encryption.encodeToString
import com.rabin2123.data.encryption.toBase64

internal operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        is ByteArray -> edit {it.putString(key, value.encodeToString())}
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

internal inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

internal inline operator fun <reified T : Any?> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        ByteArray::class -> getString(key, defaultValue as? String)?.toBase64() as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

internal fun SharedPreferences.drop() {
    edit().clear().apply()
}
