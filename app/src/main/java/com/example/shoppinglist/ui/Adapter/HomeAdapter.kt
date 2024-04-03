package com.example.shoppinglist.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ViewHolderItemBinding
import com.example.shoppinglist.ui.HomeListItem

class HomeAdapter(val listener: (Long, Boolean) -> Unit, val lis: (Long) -> Unit):
    ListAdapter<HomeListItem, HomeAdapter.ViewHolder>(Comp()) {
    var items = listOf<HomeListItem>()

        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View, val listener: (Long, Boolean) -> Unit, val lis: (Long) -> Unit):
        RecyclerView.ViewHolder(view){
        private val binding = ViewHolderItemBinding.bind(view)

        fun bind(item: HomeListItem) = with(binding) {
            checkbox.isChecked = item.isChecked
            checkbox.isEnabled = !item.isChecked
            checkbox.setOnCheckedChangeListener { buttonView, isChecked->
                listener(item.id, isChecked)
            }
            tvName.text = item.name
            tvTime.isGone = item.datatime.isNullOrBlank()
            tvTime.text = item.datatime
            deleteB.setOnClickListener {
                lis(item.id)
            }
        }
    }

    class Comp: DiffUtil.ItemCallback<HomeListItem>(){
        override fun areItemsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean {
            return  oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item, parent, false)
        return ViewHolder(view, listener, lis)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
