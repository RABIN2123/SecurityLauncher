package com.rabin2123.app.adminsettings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rabin2123.app.adminsettings.adapter.GlobalAppListRecyclerAdapter
import com.rabin2123.app.databinding.FragmentLauncherSettingsBinding
import com.rabin2123.app.services.filechecker.FileSystemObserverService
import com.rabin2123.domain.models.SettingsObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.launch

class LauncherSettingsFragment : Fragment() {

    private val vm: LauncherSettingsViewModel by viewModel()

    private val adapter by lazy {
        GlobalAppListRecyclerAdapter(

        )
    }

    private var binding: FragmentLauncherSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentLauncherSettingsBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        dataObserver()
        switchStateObserver()
    }

    private fun initUi() {
        binding?.apply {
            globalAppList.adapter = adapter
            buttonSaveLauncherSetting.setOnClickListener {
                val settings = SettingsObject(
                    sendToMlBazaar = switchFileObserverService.isChecked,
                    blockSettings = switchBlockPhoneSettings.isChecked,
                    blockGps = switchBlockGps.isChecked,
                    blockUsb = switchBlockUsbAccess.isChecked,
                    blockCamera = switchBlockCamera.isChecked
                )
                vm.saveLauncherSettings(settings, adapter.getAllowedAppList(), requireContext().applicationContext)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            buttonExitLauncherSetting.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun dataObserver() {
        lifecycleScope.launch {
            vm.listApp.collect { value ->
                adapter.submitList(value)
            }
        }
    }

    private fun switchStateObserver() {
        lifecycleScope.launch {
            vm.settingList.collect { data ->
                data?.apply {
                    binding?.apply {
                        switchFileObserverService.isChecked = sendToMlBazaar
                        switchBlockPhoneSettings.isChecked = blockSettings
                        switchBlockGps.isChecked = blockGps
                        switchBlockUsbAccess.isChecked = blockUsb
                        switchBlockCamera.isChecked = blockCamera
                    }
                }

            }
        }
    }
}