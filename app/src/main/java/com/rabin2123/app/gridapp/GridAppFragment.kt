package com.rabin2123.app.gridapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rabin2123.app.R
import com.rabin2123.app.adminsettings.LauncherSettingsFragment
import com.rabin2123.app.databinding.FragmentGridAppBinding
import com.rabin2123.app.gridapp.adapter.AppRecyclerAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GridAppFragment : Fragment() {

    private val vm: GridAppViewModel by viewModel()

    private val adapter by lazy {
        AppRecyclerAdapter(
            onItemClicked = onItemClicked
        )
    }

    private val onItemClicked: (String) -> Unit = { item ->
        if (item != "launcher_settings") {
            val launchAppIntent = context?.packageManager?.getLaunchIntentForPackage(item)
            if (launchAppIntent != null)
                startActivity(launchAppIntent)
        } else {
//            callDialog()
            openSettings()
        }
    }

//    private fun callDialog() {
//        val dialog = AlertDialog.Builder(requireContext())
//        val binding = DialogLoginWindowBinding.inflate(layoutInflater).apply {
//            buttonLogin.setOnClickListener {
//                vm.checkPassword(editTextPassword.text.toString())
//            }
//            buttonCancel.setOnClickListener {
//
//            }
//
//        }
//        dialog.setView(binding.root).create()
//        dialog.show()
//    }

    private fun openSettings() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.activity_home, LauncherSettingsFragment())
            addToBackStack(null)
            commit()
        }
    }


    private var binding: FragmentGridAppBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridAppBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        dataListener()
    }

    private fun initUi() {
        binding?.appList?.adapter = adapter
    }

    private fun dataListener() {
        lifecycleScope.launch {
            vm.listApp.collect { value ->
                adapter.submitList(value)
            }
        }
//        lifecycleScope.launch {
//            vm.resultCheckPassword.collect { value ->
//                if (value) openSettings()
//            }
//        }
    }
}