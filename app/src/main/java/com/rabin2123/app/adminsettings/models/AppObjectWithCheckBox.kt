package com.rabin2123.app.adminsettings.models

/**
 * data type for app list with checkbox
 *
 * @property name app name
 * @property packageName app package name
 * @property isChecked turn on/off access for user
 */
data class AppObjectWithCheckBox(
    val name: String,
    val packageName: String,
    val isChecked: Boolean
)
