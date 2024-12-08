package com.rabin2123.app.alertdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.rabin2123.app.R
import com.rabin2123.app.adminsettings.LauncherSettingsFragment
import com.rabin2123.app.adminsettings.LauncherSettingsViewModel
import com.rabin2123.app.databinding.DialogLoginWindowBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputPasswordDialog(private val state: String) : DialogFragment() {
    private val vm: LauncherSettingsViewModel by viewModel()
    private var binding: DialogLoginWindowBinding? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dataObserver()
        return activity?.run {
            binding = DialogLoginWindowBinding.inflate(layoutInflater).apply {
                if (state == STATE_CHANGE) {
                    editTextNewPassword.visibility = View.VISIBLE
                    textViewNewPassword.visibility = View.VISIBLE
                    textView.text = requireActivity().getString(R.string.string_old_password)
                    editTextPassword.hint =
                        requireActivity().getString(R.string.hint_string_old_password)
                    buttonLogin.text =
                        requireActivity().getString(R.string.string_change_password_button)
                }
                buttonLogin.setOnClickListener {
                    if (state == STATE_LOGIN) {
                        vm.checkPassword(editTextPassword.text.toString().trim())
                    } else {
                        vm.changePassword(
                            editTextPassword.text.toString().trim(),
                            editTextNewPassword.text.toString().trim()
                        )
                    }

                }
                buttonCancel.setOnClickListener {
                    dismiss()
                }
            }
            AlertDialog.Builder(this).apply {
                setView(binding?.root)
            }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * password correctness observer
     *
     */
    private fun dataObserver() {
        lifecycleScope.launch {
            vm.resultCheckPassword.collect { value ->
                Log.d("TAG!", "Dialog: $value")
                if (value) openSettings()
            }
        }
    }

    /**
     * open launcher settings
     *
     */
    private fun openSettings() {
        dismiss()
        if (state == STATE_LOGIN) {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.apply {
                replace(R.id.activity_home, LauncherSettingsFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    companion object {
        const val TAG = "InputPasswordDialog"
        const val STATE_LOGIN = "LOGIN"
        const val STATE_CHANGE = "CHANGE"
    }
}