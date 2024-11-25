package com.rabin2123.app.adminsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rabin2123.app.adminsettings.adapter.GlobalAppListRecyclerAdapter
import com.rabin2123.app.databinding.FragmentLauncherSettingsBinding
import com.rabin2123.app.utils.KioskUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class LauncherSettingsFragment : Fragment() {

    private val kioskUtil: KioskUtil = get()

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
        dataListener()
    }

    private fun initUi() {
        binding?.apply {
            globalAppList.adapter = adapter
            buttonSaveLauncherSetting.setOnClickListener {
                vm.saveLauncherSettings(adapter.getAllowedAppList())
                if(switchBlockPhoneSettings.isChecked) {
                    kioskUtil.blockApps(arrayOf("com.android.settings"), true)
                } else {
                    kioskUtil.blockApps(arrayOf("com.android.settings"), false)
                }
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            buttonExitLauncherSetting.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun dataListener() {
        lifecycleScope.launch {
            vm.listApp.collect() { value ->
                adapter.submitList(value)
            }
        }
    }
}