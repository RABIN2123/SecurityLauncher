package com.rabin2123.app.adminsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rabin2123.app.adminsettings.adapter.GlobalAppListRecyclerAdapter
import com.rabin2123.app.databinding.FragmentLauncherSettingsBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.launch

class AdminSettingsFragment : Fragment() {

    private val vm: AdminSettingsViewModel by viewModel()

    private val adapter by lazy {
        GlobalAppListRecyclerAdapter(

        )
    }
    private val onItemClicked: () -> Unit = {
        vm.saveLauncherSettings()
    }

    private var binding: FragmentLauncherSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentLauncherSettingsBinding.inflate(LayoutInflater.from(context), container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        dataListener()
    }

    private fun initUi() {
        binding?.globalAppList?.adapter = adapter
    }

    private fun dataListener() {
        lifecycleScope.launch {
            vm.listApp.collect() {value ->
                adapter.submitList(value)
            }
        }
    }
}