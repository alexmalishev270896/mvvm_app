package com.alex_malishev.presentation_layer.ui.base

interface RecyclerClickListener {

    interface OnClickListener<T>{
        fun onClick(position: Int, item: T)
    }

    interface OnLongClickListener<T>{
        fun onLongClick(position: Int, item: T): Boolean
    }
}