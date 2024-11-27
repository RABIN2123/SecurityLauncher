package com.rabin2123.app.adminsettings.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rabin2123.app.adminsettings.models.AppObjectWithCheckBox
import com.rabin2123.app.databinding.FragmentItemOfGlobalAppListBinding

class GlobalAppListRecyclerAdapter() :
    ListAdapter<AppObjectWithCheckBox, GlobalAppListRecyclerAdapter.MyViewHolder>(ItemDiffCallBack()) {
    private val checkedStates = SparseBooleanArray()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentItemOfGlobalAppListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun getAllowedAppList(): List<AppObjectWithCheckBox> {
        return currentList.filterIndexed{index, _ -> checkedStates[index, false]}
    }

    inner class MyViewHolder(
        private val binding: FragmentItemOfGlobalAppListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(appObject: AppObjectWithCheckBox) {
            with(binding) {
                appName.text = appObject.name
                checkboxApp.isChecked = appObject.isChecked
                checkedStates.put(position, appObject.isChecked)
                checkboxApp.setOnCheckedChangeListener { _, isChecked ->
                    checkedStates.put(position, isChecked)
                }
            }
        }
    }

    class ItemDiffCallBack : DiffUtil.ItemCallback<AppObjectWithCheckBox>() {
        override fun areItemsTheSame(oldItem: AppObjectWithCheckBox, newItem: AppObjectWithCheckBox): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: AppObjectWithCheckBox, newItem: AppObjectWithCheckBox): Boolean {
            return oldItem == newItem
        }
    }


}