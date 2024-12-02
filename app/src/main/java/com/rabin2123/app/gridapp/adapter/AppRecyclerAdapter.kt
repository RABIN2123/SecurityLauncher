package com.rabin2123.app.gridapp.adapter

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabin2123.app.R
import com.rabin2123.app.databinding.FragmentIconAppBinding
import com.rabin2123.domain.models.AppObject

/**
 * RecyclerAdapter for GridAppFragment
 *
 * @property onItemClicked opens the app you clicked on
 */
class AppRecyclerAdapter(private val onItemClicked: (String) -> Unit) :
    ListAdapter<AppObject, AppRecyclerAdapter.MyViewHolder>(
        ItemDiffCallBack()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FragmentIconAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                    Glide.with(root.context)
                        .load(
                            if (appObject.packageName != "launcher_settings") getAppIcon(
                                appObject.packageName,
                                binding.root.context
                            ) else R.mipmap.ic_launcher_settings_round
                        ).into(appIcon)
                    itemApp.setOnClickListener {
                        onItemClicked(appObject.packageName)
                    }
                appName.text = appObject.name
            }
        }

        /**
         * get icon for app
         *
         * @param packageName app package name
         * @param context context
         * @return icon for app
         */
        private fun getAppIcon(packageName: String, context: Context): Drawable {
            val pm = context.packageManager
            val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))
            } else {
                pm.getApplicationInfo(packageName, 0)
            }
            return appInfo.loadIcon(pm)
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

