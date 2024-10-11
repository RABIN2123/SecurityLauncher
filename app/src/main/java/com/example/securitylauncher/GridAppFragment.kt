package com.example.securitylauncher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.data.applist.AppListRepositoryImpl
import com.example.data.applist.models.AppObject
import com.example.securitylauncher.databinding.FragmentGridAppBinding
import kotlinx.coroutines.launch

class GridAppFragment : Fragment() {
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
    }

    private fun initUi() {
        binding?.apply {
            appList.adapter = adapter
        }
        val testList = mutableListOf<AppObject>()
        val t = AppListRepositoryImpl()
        lifecycleScope.launch {
            this@GridAppFragment.context?.let { testList.addAll(t.getAppList(it.applicationContext)) }
        }
        adapter.submitList(testList)
    }
}