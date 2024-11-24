package com.rabin2123.app.adminsettings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rabin2123.app.databinding.FragmentItemOfGlobalAppListBinding
import com.rabin2123.domain.models.AppObject

class GlobalAppListRecyclerAdapter() :
    ListAdapter<AppObject, GlobalAppListRecyclerAdapter.MyViewHolder>(ItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentItemOfGlobalAppListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

        class MyViewHolder(
            private val binding: FragmentItemOfGlobalAppListBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(appObject: AppObject) {
                with(binding) {
                    appName.text = appObject.name
                }
            }
        }

        class ItemDiffCallBack : DiffUtil.ItemCallback<AppObject>() {
        override fun areItemsTheSame(oldItem: AppObject, newItem: AppObject): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: AppObject, newItem: AppObject): Boolean {
            return oldItem == newItem
        }
    }


}