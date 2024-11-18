package com.rabin2123.app.gridapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
        val launchAppIntent = context?.packageManager?.getLaunchIntentForPackage(item)
        if (launchAppIntent != null)
            startActivity(launchAppIntent)
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
            vm.listApp.collect {value ->
                adapter.submitList(value)
            }
        }
    }
}