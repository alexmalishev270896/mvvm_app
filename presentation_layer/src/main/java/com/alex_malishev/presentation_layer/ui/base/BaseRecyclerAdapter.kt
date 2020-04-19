package com.alex_malishev.presentation_layer.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

abstract class BaseRecyclerAdapter<T : RecyclerView.ViewHolder, O> : RecyclerView.Adapter<T>() {

    var onItemClickListener: RecyclerClickListener.OnClickListener<O>? = null
    var onItemLongClickListener: RecyclerClickListener.OnLongClickListener<O>? = null
    protected var collection: MutableList<O> = ArrayList()

    fun addAll(objects: List<O>) {
        collection.addAll(objects)
        notifyDataSetChanged()
    }

    fun addAllWithoutNotify(objects: List<O>) {
        collection.addAll(objects)
    }

    fun clear() {
        collection.clear()
        notifyDataSetChanged()
    }

    fun add(o: O) {
        val index = collection.indexOf(o)
        if (index != -1) {
            collection[index] = o
            notifyItemChanged(index)
        } else {
            collection.add(o)
            notifyItemInserted(collection.size - 1)
        }
    }

    operator fun get(position: Int): O? {
        return if (position in 0 until itemCount) collection[position] else null
    }

    fun clearWithoutNotify() {
        collection.clear()
    }

    fun deleteItem(position: Int) {
        collection.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick(holder)
        }
        holder.itemView.setOnLongClickListener { view: View? ->
            onItemLongClick(holder)
        }

    }

    private fun onItemClick(holder: T) {
        if (holder.adapterPosition != -1) {
            onItemClickListener?.onClick(holder.adapterPosition, collection[holder.adapterPosition])
        }
    }

    private fun onItemLongClick(holder: T): Boolean {
        return holder.adapterPosition != -1 && onItemLongClickListener?.onLongClick(
            holder.adapterPosition,
            collection[holder.adapterPosition]
        ) ?: false
    }

    override fun getItemCount(): Int {
        return collection.size
    }
}