package com.example.securitylauncher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.applist.models.AppObject
import com.example.securitylauncher.databinding.FragmentIconAppBinding


class AppRecyclerAdapter(private val onItemClicked: (String) -> Unit) : ListAdapter<AppObject, AppRecyclerAdapter.MyViewHolder>(ItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentIconAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClicked)
    }

    class MyViewHolder(
        private val binding: FragmentIconAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(appObject: AppObject, onItemClicked: (String) -> Unit) {
            with(binding) {
                Glide.with(root.context).load(appObject.icon).into(appIcon)
                appName.text = appObject.name
                itemApp.setOnClickListener {
                    onItemClicked(appObject.packageName)
                }
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

