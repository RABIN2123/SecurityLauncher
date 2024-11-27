package com.rabin2123.app.alertdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.rabin2123.app.databinding.DialogLoginWindowBinding

class InputPasswordDialog: DialogFragment() {
    private var binding: DialogLoginWindowBinding? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.run {
            binding = DialogLoginWindowBinding.inflate(layoutInflater).apply {

            }
            AlertDialog.Builder(this).apply {
                setView(binding?.root)
            }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}